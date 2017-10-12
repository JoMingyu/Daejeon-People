WISH_ADD = {
    'tags': ['위시리스트'],
    'description': '위시리스트에 여행지 추가',
    'parameters': [
        {
            'name': 'content_id',
            'description': '위시리스트에 추가할 여행지의 content id',
            'in': 'formData',
            'type': 'int'
        },
        {
            'name': 'Authorization',
            'description': 'JWT Token',
            'in': 'header',
            'type': 'str'
        }
    ],
    'responses': {
        '201': {
            'description': '위시리스트 등록 성공'
        },
        '204': {
            'description': 'content id에 해당하는 여행지가 존재하지 않거나, 이미 위시리스트에 등록되어 있음'
        }
    }
}

WISH_GET = {
    'tags': ['위시리스트'],
    'description': '위시리스트 목록 조회',
    'parameters': [
        {
            'name': 'Authorization',
            'description': 'JWT Token',
            'in': 'header',
            'type': 'str'
        }
    ],
    'responses': {
        '200': {
            'description': '위시리스트 목록 조회 성공',
            'examples': {
                'application/json': [
                    250577,
                    1622646
                ]
            }
        }
    }
}

WISH_DELETE = {
    'tags': ['위시리스트'],
    'description': '위시리스트에서 여행지 제거',
    'parameters': [
        {
            'name': 'content_id',
            'description': '위시리스트에서 제거할 content id',
            'in': 'formData',
            'type': 'int'
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
            'description': '삭제 성공'
        }
    }
}
