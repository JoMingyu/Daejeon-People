from flask_restful_swagger_2 import swagger, Resource, request
from flask_jwt import jwt_required

from db.models.chat import ChatModel


class Chat(Resource):
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


class ChatInvitation(Resource):
    @swagger.doc()
    @jwt_required()
    def post(self):
        pass
