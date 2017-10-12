from db.models.user import AccountModel


class User:
    def __init__(self, id):
        self.id = id


def authenticate(id, pw):
    if id and pw and AccountModel.objects(id=id, pw=pw):
        return User(id=id)


def identity(payload):
    return payload['identity']
