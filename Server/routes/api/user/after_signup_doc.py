FIND_ID_DEMAND = {
    'tags': ['아이디 찾기'],
    'description': '인증번호 전송',
    'parameters': [
        {
            'name': 'email or phone',
            'description': '회원가입 당시 사용한 이메일(email) 또는 핸드폰 번호(phone)',
            'in': 'query',
            'type': 'str'
        }
    ],
    'responses': {
        '200': {
            'description': '인증번호 전송 성공'
        },
        '204': {
            'description': '인증번호 전송 실패(가입되지 않은 이메일 또는 핸드폰 번호)'
        }
    }
}

FIND_ID_VERIFY = {
    'tags': [''],
    'description': '',
    'parameters': [
        {
            'name': 'email or phone',
            'description': '인증번호를 받은 이메일(email) 또는 핸드폰 번호(phone)',
            'in': 'formData',
            'type': 'str'
        },
        {
            'name': 'code',
            'description': '인증번호',
            'in': 'formData',
            'type': 'int'
        }
    ],
    'responses': {
        '201': {
            'description': '인증 성공',
            'examples': {
                'application/json': {
                    "id": "geni429"
                }
            }
        },
        '204': {
            'description': '인증 실패(올바르지 않은 인증번호)'
        }
    }
}
