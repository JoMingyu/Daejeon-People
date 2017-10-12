import uuid as u

from flask_restful_swagger_2 import swagger, Resource, request
from flask_jwt import jwt_required, current_identity

from db.models.chat import ChatModel
from db.models.user import AccountModel

from .doc import chat_doc


class Chat(Resource):
    @swagger.doc(chat_doc.CHAT_CREATE)
    @jwt_required()
    def post(self):
        title = request.form.get('title')
        client_id = current_identity
        topic = str(u.uuid4())

        chat_rooms = list(AccountModel.objects(id=client_id).first().chat_rooms)
        # Get existing chat rooms
        chat_rooms.append(ChatModel(topic=topic, title=title))
        # Append new chat room

        AccountModel.objects(id=client_id).first().update(chat_rooms=chat_rooms)
        # Update

        return {'topic': topic}, 201

    @swagger.doc(chat_doc.CHAT_INQUIRE)
    @jwt_required()
    def get(self):
        client_id = current_identity
        chat_rooms = [{
            'topic': chat_room.topic,
            'title': chat_room.title
        } for chat_room in AccountModel.objects(id=client_id).first().chat_rooms]

        if chat_rooms:
            return chat_rooms, 200
        else:
            return '', 204

    @swagger.doc(chat_doc.CHAT_QUIT)
    @jwt_required()
    def delete(self):
        client_id = current_identity
        topic = request.form.get('topic')

        chat_rooms = list(AccountModel.objects(id=client_id).first().chat_rooms)
        for idx, chat_room in enumerate(chat_rooms):
            if chat_room.topic == topic:
                del chat_rooms[idx]

        AccountModel.objects(id=client_id).first().update(chat_rooms=chat_rooms)

        return '', 200


class ChatInvitation(Resource):
    @swagger.doc(chat_doc.CHAT_INVITE)
    @jwt_required()
    def post(self):
        topic = request.form.get('topic')
        client_id = current_identity
        target_id = request.form.get('target_id')

        chat_rooms = list(AccountModel.objects(id=client_id).first().chat_rooms)
        try:
            for chat_room in chat_rooms:
                if chat_room.topic == topic:
                    target_rooms = list(AccountModel.objects(id=target_id).first().chat_rooms)
                    target_rooms.append(chat_room)

                    AccountModel.objects(id=target_id).first().update(target_rooms)

            return '', 201
        except AttributeError:
            return '', 204
