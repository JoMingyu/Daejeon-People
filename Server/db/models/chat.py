from mongoengine import *

from db.models.user import AccountModel


class ChatModel(Document):
    topic = StringField(primary_key=True)
    title = StringField(required=True)
    clients = ListField(ReferenceField(AccountModel))
