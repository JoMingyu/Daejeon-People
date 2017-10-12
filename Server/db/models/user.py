from datetime import date

from mongoengine import *


class AccountModel(Document):
    id = StringField(primary_key=True)
    pw = StringField(required=True)

    registration_id = StringField(required=True, unique=True)
    email = StringField(unique=True)
    phone = StringField(unique=True)
    name = StringField(required=True)
    register_date = StringField(required=True, default=str(date.today()))

    friend_requests = ListField(StringField(), default=[])
    friends = ListField(StringField(), default=[])
    wish_list = ListField(IntField())


class CertifyModel(Document):
    identity = StringField(primary_key=True)
    code = IntField(required=True)
