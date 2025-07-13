import { useState } from 'react';

import {
    Container,
    JoinBox,
    Title,
    InputForm,
    Input,
    ButtonBox,
    JoinButton,
    LoginButton,
} from "../styles/pages/Join.styles";
import { ResponseBody } from '../types/api';
import { useNavigation } from '../hooks/useNavigation';



const Join = () => {
    const { handleNavigateToLogin } = useNavigation();

    const [nameInput, setNameInput] = useState<string>('');
    const [passwordInput, setPasswordInput] = useState<string>('');



    const handleNameInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setNameInput(e.target.value);
    };

    const handlePasswordInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPasswordInput(e.target.value);
    };

    const handleJoinRequest = () => {
        const body = {
            name: nameInput,
            password: passwordInput,
        }
        
        const requestOptions: RequestInit = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify(body),
        }

        fetch('http://localhost:8080/api/v1/users', requestOptions)
            .then((response: Response) => {
                if(response.ok) {
                    alert('[회원가입 완료]');
                    handleNavigateToLogin();
                    return;
                }
                
                response.json().then((responseBody: ResponseBody) => {
                    alert(`
                    [회원가입 실패]
                    status: ${responseBody.status}
                    message: ${responseBody.message}
                    timestamp: ${responseBody.timestamp}`)
                })
                
            })
            .catch((error: Error) => {
                console.error(error.message);
            })
            .finally();
    }



    return (
        <Container>
            <JoinBox>
                <Title>Join</Title>
                <InputForm>
                    <Input 
                        placeholder="name"
                        type="text"
                        value={nameInput}
                        onChange={handleNameInputChange}
                    ></Input>
                    <Input 
                        placeholder="password"
                        type="password"
                        value={passwordInput}
                        onChange={handlePasswordInputChange}
                    ></Input>
                </InputForm>

                <ButtonBox>
                    <JoinButton onClick={handleJoinRequest}>join</JoinButton>
                    <LoginButton onClick={handleNavigateToLogin}>login</LoginButton>
                </ButtonBox>
            </JoinBox>
        

        </Container>
    )
}

export default Join;