CHAT_CREATE = {
    'tags': ['채팅'],
    'description': '채팅방 만들기',
    'parameters': [
        {
            'name': 'title',
            'description': '채팅방 제목',
            'in': 'formData',
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
        '201': {
            'description': '채팅방 생성 성공',
            'examples': {
                'application/json': {
                    'topic': '47e02b18-b23f-45ff-9fd2-e5a568febf88'
                }
            }
        }
    }
}

CHAT_INQUIRE = {
    'tags': ['채팅'],
    'description': '소속된 채팅방 목록 조회',
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
            'description': '소속된 채팅방이 1개 이상 있음',
            'examples': {
                'application/json': [
                    {
                        "topic": "67a6ff4a-f2ba-440b-8b58-62866103c558",
                        "title": "qwe"
                    },
                    {
                        "topic": "f525b913-20a5-497f-b49c-0cb5312dd0db",
                        "title": "qwe"
                    },
                    {
                        "topic": "47e02b18-b23f-45ff-9fd2-e5a568febf88",
                        "title": "123"
                    }
                ]
            }
        },
        '204': {
            'description': '소속된 채팅방 없음'
        }
    }
}

CHAT_QUIT = {
    'tags': ['채팅'],
    'description': '채팅방 나가기',
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
            'description': '채팅방 나가기 성공'
        }
    }
}

CHAT_INVITE = {
    'tags': ['채팅'],
    'description': '채팅방에 친구 초대',
    'parameters': [
        {
            'name': 'topic',
            'description': '채팅방 topic',
            'in': 'formData',
            'type': 'str'
        },
        {
            'name': 'target_id',
            'description': '채팅방에 초대할 사람의 ID',
            'in': 'formData',
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
        '201': {
            'description': '채팅방 초대 성공'
        },
        '204': {
            'description': '채팅방 초대 실패(타겟 ID가 존재하지 않음)'
        }
    }
}
