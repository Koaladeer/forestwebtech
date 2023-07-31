# Simple Communication REST API

This repository contains a simple REST API designed to facilitate communication between the frontend, a database, and the ChatGPT API.

## Features

- **Frontend Communication**: The API acts as a bridge between the frontend and other services, enabling data transfer and real-time updates.

- **Database Interaction**: It provides endpoints for interaction with the database, allowing storing and retrieving relevant data for the chatbot and frontend.

- **Integration with ChatGPT API**: The API  connects to the ChatGPT API, allowing you to use of GPT-based chatbots.


### Controller Endpoints
#### POST /messages
- Description: Handles a POST request to the "/messages" endpoint and creates a new AIMessageEntity.
#### POST /chatmessages
- Description: Handles a POST request to the "/chatmessages" endpoint and creates a new ChatMessage (not used in the implementation).
#### DELETE /messages/delete/{id}
- Description: Handles a DELETE request to the "/messages/delete/{id}" endpoint and deletes an AIMessageEntity by its ID.
#### GET /messages
- Description: Handles a GET request to the "/messages" endpoint and returns a list of all AIMessageEntities.
#### POST /api/chat
- Description: Handles a POST request to the "/api/chat" endpoint and generates a chat response using the ChatGPT API.

## Service Methods

The `ForestService` class provides several methods to handle chat messages and AI messages:

- `save(ChatMessage message)`: Saves a chat message to the database and returns the saved message.
- `deleteChatMessagesByID(Long id)`: Deletes a chat message from the database based on its ID.
- `getAllChatMessages()`: Retrieves all chat messages from the database and returns them as a list.
- `save(AIMessageEntity message)`: Saves an AI message entity to the database and returns the saved entity.
- `deleteAIMessageByID(Long id)`: Deletes an AI message entity from the database based on its ID.
- `get(Long id)`: Retrieves an AI message entity from the database based on its ID. If not found, it throws a `RuntimeException`.
- `getAllAIMessages()`: Retrieves all AI message entities from the database and returns them as a list.
- `searchStringForCode(String message)`: Removes code blocks from the provided message string using regular expressions.
- `getContextMessages(UserMessage userMessage)`: Retrieves context messages based on the user message. It collects both chat messages and AI messages with a matching `sId` (search ID) and constructs a JSON string representing the context messages.
- `openAIConnection(String userMessage)`: Connects to the OpenAI API and performs a chat completion based on the user message. It sends the user message to the API and receives a response with the generated chat message.
