from flask_jwt import current_identity, jwt_required
from flask_restful_swagger_2 import Resource, request, swagger

from db.models.friend import FriendRequestsModel
from db.models.user import AccountModel


class Friend(Resource):
    # @swagger.doc()
    @jwt_required()
    def get(self):
        return AccountModel.objects(id=current_identity).first().friends, 200

    # @swagger.doc()
    @jwt_required()
    def delete(self):
        friend_id = request.form.get('friend_id')

        friends = AccountModel.objects(id=current_identity).first().friends
        del friends[friends.index(friend_id)]

        AccountModel.objects(id=current_identity).first().update(friends=friends)

        return '', 200


class FriendInvitation(Resource):
    # 요청자 관점
    # @swagger.doc()
    @jwt_required()
    def post(self):
        # 친구 요청
        receiver_id = request.form.get('receiver_id')

        if FriendRequestsModel.objects(requester_id=current_identity, receiver_id=receiver_id):
            return '', 204
        else:
            FriendRequestsModel(requester_id=current_identity, receiver_id=receiver_id).save()

            return '', 201

    # @swagger.doc()
    @jwt_required()
    def get(self):
        # 친구 요청 목록
        friend_requests = FriendRequestsModel.objects(requester_id=current_identity)

        if friend_requests:
            return [friend_request.receiver_id for friend_request in friend_requests], 200
        else:
            return '', 204

    # @swagger.doc()
    @jwt_required()
    def delete(self):
        # 친구 요청 취소
        receiver_id = request.form.get('receiver_id')

        FriendRequestsModel.objects(requester_id=current_identity, receiver_id=receiver_id).first().delete()

        return '', 200


class ReceivedFriendInvitation(Resource):
    # 수신자 입장
    # @swagger.doc()
    @jwt_required()
    def post(self):
        # 친구 수락
        requester_id = request.form.get('requester_id')

        FriendRequestsModel.objects(requester_id=requester_id, receiver_id=current_identity).first().delete()

        friends = AccountModel.objects(id=current_identity).first().friends
        friends.append(requester_id)

        AccountModel.objects(id=current_identity).first().update(friends=friends)

        return '', 201

    # @swagger.doc()
    @jwt_required()
    def get(self):
        # 친구요청 목록
        friend_requests = FriendRequestsModel.objects(receiver_id=current_identity)

        if friend_requests:
            return [friend_request.requester_id for friend_request in friend_requests], 200
        else:
            return '', 204

    # @swagger.doc()
    @jwt_required()
    def delete(self):
        # 친구 거절
        requester_id = request.form.get('requester_id')

        FriendRequestsModel.objects(requester_id=requester_id, receiver_id=current_identity).first().delete()

        return '', 200
