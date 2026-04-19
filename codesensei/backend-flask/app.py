import os
from flask import Flask, request, jsonify


def create_app() -> Flask:
    app = Flask(__name__)

    @app.get("/health")
    def health():
        return {"status": "ok"}

    @app.post("/v1/cgm/chat")
    def cgm_chat():
        data = request.get_json(silent=True) or {}
        message = (data.get("message") or "").strip()
        if not message:
            return jsonify({"error": "message is required"}), 400

        # TODO: integrate CodeFuse CGM model here.
        # For now, we return a deterministic stub response.
        response = (
            "CodeFuse CGM (stub): j'ai reçu votre message.\n\n"
            f"Message: {message}\n\n"
            "Intégration modèle: à brancher dans backend-flask/app.py"
        )
        return jsonify({"response": response})

    return app


app = create_app()


if __name__ == "__main__":
    port = int(os.getenv("PORT", "5001"))
    app.run(host="0.0.0.0", port=port, debug=False)

