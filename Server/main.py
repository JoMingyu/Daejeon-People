import threading

from flask import Flask
from flask_jwt import JWT
from flask_cors import CORS

import server_preprocessor
from support import jwt
from support.api_interactions import tour_api


def create_app():
    """
    Creates Flask instance & initialize

    Flask best practice : Application Factories
    - Use 'application factory', 'current_app'

    # What's the big deal?
    1. Testing
    2. Multiple instances running in one process
    3. from app import app -> circular imports

    :return: app
    :rtype: Flask
    """
    app = Flask(__name__)
    app.config.update(
        SECRET_KEY='!owQzm[pn;?11K',
        JWT_AUTH_URL_RULE='/signin',
        JWT_AUTH_USERNAME_KEY='id',
        JWT_AUTH_PASSWORD_KEY='pw'
    )

    CORS(app)
    JWT(app, authentication_handler=jwt.authenticate, identity_handler=jwt.identity)

    server_preprocessor.decorate(app)
    server_preprocessor.add_resources(app, api_version=0.1)

    return app

_app = create_app()


@_app.route('/test')
def index():
    return 'hello?'

if __name__ == '__main__':
    # threading.Thread(target=tour_api.parse).start()
    _app.run(debug=True)
