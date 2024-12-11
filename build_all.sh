#!/bin/bash
echo "########################## GROCERY V1"
docker build -t grocery_v1 GroceryRestAPIV1/.
echo "########################## GROCERY V2"
docker build -t grocery_v2 GroceryRestAPIV2/.
echo "########################## BOOKTOWN"
docker build -t booktown BooktownGraphQL/.
echo "########################## TASK V1"
docker build -t task_v1 tasks-manager-v1/.
echo "########################## TASK_V2"
docker build -t task_v2 tasks-manager-v2/.
echo "########################## ENCRYPT and DECRYPT"
docker build -t encrypt_amex EncryptDecrypt/encrypt/.
docker build -t decrypt_amex EncryptDecrypt/decrypt/.
echo "########################## ALL IMAGES"
docker images
echo "To remove all built images run: docker rmi grocery_v1 grocery_v2 booktown task_v1 task_v2 encrypt_amex decrypt_amex" 