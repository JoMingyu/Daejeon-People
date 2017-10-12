from flask_restful_swagger_2 import swagger, Resource, request
from flask_jwt import jwt_required, current_identity


class Friend(Resource):
    @swagger.doc()
    @jwt_required()
    def post(self):
        pass

    @swagger.doc()
    @jwt_required()
    def get(self):
        pass

    @swagger.doc()
    @jwt_required()
    def delete(self):
        pass


class FriendInvitation(Resource):
    # 요청자 입장
    @swagger.doc()
    @jwt_required()
    def post(self):
        pass

    @swagger.doc()
    @jwt_required()
    def get(self):
        pass

    @swagger.doc()
    @jwt_required()
    def delete(self):
        pass


class ReceivedFriendInvitation(Resource):
    # 수신자 입장
    @swagger.doc()
    @jwt_required()
    def post(self):
        pass

    @swagger.doc()
    @jwt_required()
    def get(self):
        pass

    @swagger.doc()
    @jwt_required()
    def delete(self):
        pass
