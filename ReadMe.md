Account-management-app
===============================

## Функционал ##

Account-management-app позволяет пользователям с различными ролями управлять банковскими счетами. Приложение поддерживает две основные роли:

- **Администратор**: имеет доступ к управлению всеми счетами, включая возможность блокировки и разблокировки счетов.
- **Пользователь**: имеет доступ только к своему счету и может выполнять операции пополнения и снятия средств, если счет не заблокирован.

### Администратор

1. **Логин**:
    - Администратор должен аутентифицироваться в системе с использованием своих учетных данных.

2. **Просмотр всех счетов**:
    - После успешного логина администратор видит список всех счетов в системе.

3. **Блокировка счета**:
    - Администратор может выбрать любой счет и заблокировать его.
    - Блокировка счета означает, что пользователь не сможет выполнять операции пополнения и снятия средств.

4. **Разблокировка счета**:
    - Администратор может выбрать любой заблокированный счет и разблокировать его.
    - Разблокированный счет позволяет пользователю выполнять операции пополнения и снятия средств.

### Пользователь

1. **Логин**:
    - Администратор должен аутентифицироваться в системе с использованием своих учетных данных.

2. **Логин**:
    - Пользователь должен аутентифицироваться в системе с использованием своих учетных данных.

3. **Просмотр личного счета**:
    - Пользователь видит только свой счет и информацию о нем.

4. **Пополнение счета**:
    - Пользователь может пополнить свой счет на определенную сумму.
    - Если счет заблокирован, операция пополнения не выполняется, и пользователю выводится сообщение об ошибке.

5. **Снятие средств**:
    - Пользователь может снять средства со своего счета.
    - Если счет заблокирован, операция снятия не выполняется, и пользователю выводится сообщение об ошибке.

## Локальный Запуск Проекта с Использованием Docker Compose

**1. Установите Docker и Docker Compose:**
- [Руководство по Установке Docker](https://docs.docker.com/get-docker/)
- [Руководство по Установке Docker Compose](https://docs.docker.com/compose/install/)

**2. Клонируйте Репозиторий:**
   ```bash
   git clone https://github.com/Ladzislau/ams
   cd ams
   ```

**3. Запустите Docker Compose:**

   ```bash
docker compose up
   ```
Эта команда создаст и запустит контейнер postgresql в качестве БД.

**4. Запустите Spring Boot приложение:**

```bash
./gradlew bootRun
   ```

**5. Документация OpenAPI:**

Получить документацию OpenAPI в виде JSON можно перейдя по адресу http://localhost:8080/v3/api-docs

## Данные для входа

### АДМИН

- **Email**: `superadmin@ladzislau-bank.by`
- **Пароль**: `heyHES`

### ПОЛЬЗОВАТЕЛЬ

- **Пользователь 1**:
    - **Email**: `johndoe@mail.by`
    - **Пароль**: `blackTesla`

- **Пользователь 2**:
    - **Email**: `gustavo@mail.by`
    - **Пароль**: `losPollos`

## Описание API

### Администратор

1. **Логин**
    - **Endpoint**: `/v1/users/login`
    - **Метод**: PATCH
    - **Описание**: Аутентификация администратора в системе.
    - **Запрос**:
      ```json
      {
        "email": "string",
        "password": "string"
      }
      ```
    - **Ответ**:
      ```json
      {
        "jwt": "string"
      }
      ```

2. **Просмотр всех счетов**
    - **Endpoint**: `/v1/admin/accounts`
    - **Метод**: GET
    - **Описание**: Получение списка всех счетов в системе.
    - **Параметры запроса**:
        - `pageNumber` (опционально) - Номер страницы (по умолчанию: 0)
        - `pageSize` (опционально) - Количество записей на странице (по умолчанию: 20)
    - **Ответ**:
      ```json
      {
        "content": [
          {
            "id": 0,
            "iban": "string",
            "balance": 0.0,
            "blocked": true,
            "userId": 0
          }
        ],
        "page": {
          "size": 0,
          "number": 0,
          "totalElements": 0,
          "totalPages": 0
        }
      }
      ```

3. **Блокировка счета**
    - **Endpoint**: `/v1/admin/accounts/{accountId}/block`
    - **Метод**: PATCH
    - **Описание**: Блокировка счета.
    - **Параметры запроса**:
        - `accountId` - Идентификатор счета
    - **Ответ**:
      ```json
      {
        "id": 0,
        "iban": "string",
        "balance": 0.0,
        "blocked": true,
        "userId": 0
      }
      ```

4. **Разблокировка счета**
    - **Endpoint**: `/v1/admin/accounts/{accountId}/unblock`
    - **Метод**: PATCH
    - **Описание**: Разблокировка счета.
    - **Параметры запроса**:
        - `accountId` - Идентификатор счета
    - **Ответ**:
      ```json
      {
        "id": 0,
        "iban": "string",
        "balance": 0.0,
        "blocked": false,
        "userId": 0
      }
      ```

### Пользователь

1. **Регистрация**
    - **Endpoint**: `/v1/users/registration`
    - **Метод**: POST
    - **Описание**: Регистрация нового пользователя.
    - **Запрос**:
      ```json
      {
        "email": "string",
        "firstName": "string",
        "lastName": "string",
        "password": "string"
      }
      ```
    - **Ответ**:
      ```json
      {
        "jwt": "string"
      }
      ```

2. **Логин**
    - **Endpoint**: `/v1/users/login`
    - **Метод**: PATCH
    - **Описание**: Аутентификация пользователя в системе.
    - **Запрос**:
      ```json
      {
        "email": "string",
        "password": "string"
      }
      ```
    - **Ответ**:
      ```json
      {
        "jwt": "string"
      }
      ```

3. **Просмотр личного счета**
    - **Endpoint**: `/v1/accounts/me`
    - **Метод**: GET
    - **Описание**: Получение информации о собственном счете.
    - **Ответ**:
      ```json
      {
        "id": 0,
        "iban": "string",
        "balance": 0.0,
        "blocked": false,
        "userId": 0
      }
      ```

4. **Пополнение счета**
    - **Endpoint**: `/v1/accounts/me/deposit`
    - **Метод**: PATCH
    - **Описание**: Пополнение собственного счета.
    - **Запрос**:
      ```json
      {
        "amount": 0.0
      }
      ```
    - **Ответ**:
      ```json
      {
        "accountId": 0,
        "iban": "string",
        "updatedBalance": "string",
        "userId": 0,
        "transactionAmount": "string"
      }
      ```

5. **Снятие средств**
    - **Endpoint**: `/v1/accounts/me/withdraw`
    - **Метод**: PATCH
    - **Описание**: Снятие средств со собственного счета.
    - **Запрос**:
      ```json
      {
        "amount": 0.0
      }
      ```
    - **Ответ**:
      ```json
      {
        "accountId": 0,
        "iban": "string",
        "updatedBalance": "string",
        "userId": 0,
        "transactionAmount": "string"
      }
      ```
