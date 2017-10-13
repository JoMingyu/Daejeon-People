FRIEND_LIST = {
    'tags': ['친구'],
    'description': '친구 목록 조회',
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
            'description': '친구 있음',
            'examples': {
                'application/json': [
                    'geni429',
                    'city7310',
                    'PlanB'
                ]
            }
        },
        '204': {
            'description': '슬프게도 친구 없음'
        }
    }
}

FRIEND_DELETE = {
    'tags': ['친구'],
    'description': '친구 삭제',
    'parameters': [
        {
            'name': 'friend_id',
            'description': '삭제할 친구의 id',
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
        '200': {
            'description': '친구 삭제 완료'
        }
    }
}

FRIEND_INVITE = {
    'tags': ['친구 요청'],
    'description': '친구 요청',
    'parameters': [
        {
            'name': 'receiver_id',
            'description': '친구를 요청할 ID',
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
            'description': '친구 요청 성공'
        },
        '204': {
            'description': '이미 요청됨'
        }
    }
}

FRIEND_INVITE_LIST = {
    'tags': ['친구 요청'],
    'description': '자신이 요청한 친구 요청 목록 조회',
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
            'description': '받은 친구 요청 있음',
            'examples': {
                'application/json': [
                    'geni123',
                    'geni415'
                ]
            }
        },
        '204': {
            'description': '받은 친구 요청 없음'
        }
    }
}

FRIEND_INVITE_WITHDRAW = {
    'tags': ['친구 요청'],
    'description': '자신이 보낸 친구 요청 취소',
    'parameters': [
        {
            'name': 'receiver_id',
            'description': '친구 요청을 취소할 ID',
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
        '200': {
            'description': '친구 요청 취소 성공'
        }
    }
}

FRIEND_ACCEPT = {
    'tags': ['친구 요청'],
    'description': '친구 요청 수락',
    'parameters': [
        {
            'name': 'requester_id',
            'description': '친구 요청을 보낸 사람의 ID',
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
            'description': '친구 수락 성공'
        }
    }
}

FRIEND_RECEIVED_LIST = {
    'tags': ['친구 요청'],
    'description': '받은 친구 요청 목록 조회',
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
            'description': '받은 친구 요청 있음',
            'examples': {
                'application/json': [
                    'geni412',
                    'JoMingyuSibal'
                ]
            }
        },
        '204': {
            'description': '받은 친구 요청 없음'
        }
    }
}

FRIEND_REFUSE = {
    'tags': ['친구 요청'],
    'description': '친구 요청 거절',
    'parameters': [
        {
            'name': 'requester_id',
            'description': '친구 요청을 보낸 사람의 ID',
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
        '200': {
            'description': '거절 성공'
        }
    }
}
