from flask import Flask, current_app, request
from flask_restful_swagger_2 import Api
from logging.handlers import RotatingFileHandler
from logging import Formatter, INFO

from routes.api.user.account.account import EmailCheck, PhoneCheck, EmailCertify, PhoneCertify, Signup


def decorate(app):
    """
    Decorate request contexts to logging

    :param app: flask.Flask instance

    :type app: Flask

    :rtype: None
    """
    @app.before_first_request
    def before_first_request():
        def make_logger():
            handler = RotatingFileHandler('server_log.log', maxBytes=100000, backupCount=5)
            handler.setFormatter(Formatter("[%(asctime)s] %(levelname)s - %(message)s"))

            current_app.logger.addHandler(handler)
            current_app.logger.setLevel(INFO)

        make_logger()
        current_app.logger.info('------ Logger Initialized ------')

    @app.before_request
    def before_request():
        current_app.logger.info('Requested from {0} [ {1} {2} ]'.format(request.host, request.method, request.url))
        current_app.logger.info('Request values : {0}'.format(request.values))

    @app.after_request
    def after_request(response):
        current_app.logger.info('Respond : {0}'.format(response.status))

        return response

    @app.teardown_request
    def teardown_request(exception):
        if not exception:
            current_app.logger.info('Teardown request successfully.')

    @app.teardown_appcontext
    def teardown_appcontext(exception):
        if not exception:
            current_app.logger.info('Teardown appcontext successfully.')


def add_resources(app, api_version):
    """
    Add resources to Flask instance

    :param app: flask.Flask instance
    :param api_version: Api version

    :type app: Flask
    :type api_version: float

    :rtype: None
    """
    api = Api(app, api_version=api_version)
    api.add_resource(EmailCheck, '/check/email')
    api.add_resource(PhoneCheck, '/check/phone')
    api.add_resource(EmailCertify, '/certify/email')
    api.add_resource(PhoneCertify, '/certify/phone')
    api.add_resource(Signup, '/signup')
