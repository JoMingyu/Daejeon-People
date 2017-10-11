EMAIL_CHECK = {
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
}

PHONE_CHECK = {
    'tags': ['회원가입'],
    'description': '핸드폰 번호 중복 체크',
    'parameters': [
        {
            'name': 'email',
            'description': '중복체크할 핸드폰 번호',
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
}

EMAIL_CERTIFY_GET = {
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
}

EMAIL_CERTIFY_POST = {
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
}

PHONE_CERTIFY = {

}

SIGNUP = {
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
}
