from db.mongo import *


class FriendRequestsModel(Document):
    requester_id = StringField()
    receiver_id = StringField()
