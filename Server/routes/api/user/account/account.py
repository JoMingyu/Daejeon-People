import random
import os
from smtplib import SMTP
from email.mime.text import MIMEText

from flask_restful_swagger_2 import swagger, Resource, request

from db.models.user import AccountModel, CertifyModel


class EmailCheck(Resource):
    @swagger.doc({
        'tags': ['회원가입'],
        'description': '이메일 중복 체크',
        'parameters': [
            {
                'name': 'email',
                'description': '중복체크할 이메일 주소(****@****.***)',
                'in': 'formData',
                'type': 'str'
            }
        ],
        'responses': {
            '201': {
                'description': '중복되지 않음'
            },
            '204': {
                'description': '중복됨'
            }
        }
    })
    def post(self):
        if AccountModel.objects(email=request.form.get('email')):
            return '', 204
        else:
            return '', 201


class PhoneCheck(Resource):
    def post(self):
        if AccountModel.objects(phone=request.form.get('phone')):
            return '', 204
        else:
            return '', 201


def get_certify_code():
    return int('%06d' % random.randint(0, 999999))


class EmailCertify(Resource):
    @classmethod
    def send_certify_mail(cls, dst_email, code):
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

    @swagger.doc({
        'tags': ['회원가입'],
        'description': '이메일 인증 코드 발급',
        'parameters': [
            {
                'name': 'email',
                'description': '인증 코드를 발급할 이메일 주소(****@****.***)',
                'in': 'query',
                'type': 'str'
            }
        ],
        'responses': {
            '200': {
                'description': '인증 코드 전송 완료'
            }
        }
    })
    def get(self):
        email = request.args.get('email')
        code = get_certify_code()

        CertifyModel(identity=email, code=code).save()
        self.send_certify_mail(email, code)

        return '', 200

    @swagger.doc({
        'tags': ['회원가입'],
        'description': '이메일 인증',
        'parameters': [
            {
                'name': 'email',
                'description': '인증 코드를 받은 이메일 주소(****@****.***)',
                'in': 'formData',
                'type': 'str'
            },
            {
                'name': 'code',
                'description': '인증 코드',
                'in': 'formData',
                'type': 'int'
            }
        ],
        'responses': {
            '201': {
                'description': '이메일 인증 성공'
            },
            '204': {
                'description': '이메일 인증 실패'
            }
        }
    })
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
    @swagger.doc({
        'tags': ['회원가입'],
        'description': '이메일 인증',
        'parameters': [
            {
                'name': 'id',
                'description': '사용자 ID',
                'in': 'formData',
                'type': 'str'
            },
            {
                'name': 'pw',
                'description': '사용자 Password',
                'in': 'formData',
                'type': 'str'
            },
            {
                'name': 'registration_id',
                'description': 'Firebase 토큰',
                'in': 'formData',
                'type': 'str'
            },
            {
                'name': 'email(optional)',
                'description': '이메일',
                'in': 'formData',
                'type': 'str'
            },
            {
                'name': 'phone(optional)',
                'description': '핸드폰 번호',
                'in': 'formData',
                'type': 'str'
            },
            {
                'name': 'name',
                'description': '사용자 이름',
                'in': 'formData',
                'type': 'str'
            }
        ],
        'responses': {
            '201': {
                'description': '회원가입 성공'
            },
            '204': {
                'description': '회원가입 실패(이미 가입된 아이디)'
            }
        }
    })
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
