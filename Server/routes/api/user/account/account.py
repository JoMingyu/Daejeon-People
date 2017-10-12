import random
import os
from smtplib import SMTP
from email.mime.text import MIMEText

from flask_restful_swagger_2 import swagger, Resource, request

from db.models.user import AccountModel, CertifyModel
from .doc import account_doc


class EmailCheck(Resource):
    @swagger.doc(account_doc.EMAIL_CHECK)
    def post(self):
        if AccountModel.objects(email=request.form.get('email')):
            return '', 204
        else:
            return '', 201


class PhoneCheck(Resource):
    @swagger.doc(account_doc.PHONE_CHECK)
    def post(self):
        if AccountModel.objects(phone=request.form.get('phone')):
            return '', 204
        else:
            return '', 201


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


class EmailCertify(Resource):
    @swagger.doc(account_doc.EMAIL_CERTIFY_GET)
    def get(self):
        email = request.args.get('email')
        code = get_certify_code()

        CertifyModel(identity=email, code=code).save()
        send_certify_mail(email, code)

        return '', 200

    @swagger.doc(account_doc.EMAIL_CERTIFY_POST)
    def post(self):
        email = request.form.get('email')
        code = request.form.get('code', type=int)

        if CertifyModel.objects(identity=email, code=code):
            CertifyModel.objects(identity=email).first().delete()

            return '', 201
        else:
            return '', 204


class PhoneCertify(Resource):
    def get(self):
        pass

    def post(self):
        pass


class Signup(Resource):
    @swagger.doc(account_doc.SIGNUP)
    def post(self):
        id = request.form.get('id')
        pw = request.form.get('pw')
        registration_id = request.form.get('registration_id')
        email = request.form.get('email', default=None)
        phone = request.form.get('phone', default=None)
        name = request.form.get('name')

        if AccountModel.objects(id=id):
            return '', 204
        else:
            AccountModel.objects(registration_id=registration_id).delete()
            AccountModel(id=id, pw=pw, registration_id=registration_id, email=email, phone=phone, name=name).save()

            return '', 201
