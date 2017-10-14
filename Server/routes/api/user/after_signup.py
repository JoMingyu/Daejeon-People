import os
import random

from email.mime.text import MIMEText
from smtplib import SMTP

from flask_restful_swagger_2 import Resource, request, swagger

from db.models.user import AccountModel, CertifyModel
from routes.api.user import after_signup_doc


def get_certify_code():
    return int('%06d' % random.randint(0, 999999))


def send_certify_mail(dst_email, code):
    smtp_id = os.getenv('SMTP_ID')
    smtp_pw = os.getenv('SMTP_PW')

    smtp = SMTP('smtp.naver.com', 587)
    smtp.starttls()
    smtp.login(smtp_id, smtp_pw)

    message = MIMEText('Code : {0}'.format(code), _charset='utf-8')
    message['subject'] = '[대전사람] 이메일 인증 코드입니다.'
    message['from'] = smtp_id + '@naver.com'
    message['to'] = dst_email

    smtp.sendmail(smtp_id + '@naver.com', dst_email, message.as_string())
    smtp.quit()


class FindID(Resource):
    @swagger.doc(after_signup_doc.FIND_ID_DEMAND)
    def get(self):
        email = request.args.get('email', default=None)
        phone = request.args.get('phone', default=None)
        code = get_certify_code()

        if email and AccountModel.objects(email=email):
            CertifyModel(identity=email, code=code).save()
            send_certify_mail(email, code)

            return '', 200
        elif phone and AccountModel.objects(phone=phone):
            pass
        else:
            return '', 204

    @swagger.doc(after_signup_doc.FIND_ID_VERIFY)
    def post(self):
        email = request.form.get('email', default=None)
        phone = request.form.get('phone', default=None)
        code = request.form.get('code', type=int)

        if email:
            if CertifyModel.objects(identity=email, code=code):
                CertifyModel.objects(identity=email, code=code).first().delete()

                return {'id': AccountModel.objects(email=email).first().id}, 201
            else:
                return '', 204
        elif phone:
            if CertifyModel.objects(identity=phone, code=code):
                CertifyModel.objects(identity=phone, code=code).first().delete()

                return {'id': AccountModel.objects(phone=phone).first().id}, 201
            else:
                return '', 204


class FindPW(Resource):
    def get(self):
        pass

    def post(self):
        pass
