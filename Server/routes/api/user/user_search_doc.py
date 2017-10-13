USER_SEARCH = {
    'tags': ['사용자 정보'],
    'description': 'ID 기반 사용자 검색',
    'parameters': [
        {
            'name': 'id',
            'description': '검색할 id',
            'in': 'query',
            'type': 'str'
        },
        {
            'name': 'Authorization',
            'description': 'JWT Token',
            'in': 'header',
            'type': 'str'
        }
    ],
    'responses': {
        '200': {
            'description': '검색 성공',
            'examples': {
                'application/json': {
                    "id": "city7310",
                    "email": "city7310@naver.com",
                    "phone": "01012345678",
                    "name": "조민규",
                    "friend_requested": True
                }
            }
        },
        '204': {
            'description': '검색 실패(사용자 없음)'
        }
    }
}
