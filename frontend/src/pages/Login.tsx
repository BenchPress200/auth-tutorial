import { useState, useEffect } from 'react';

import { 
    Container, 
    LoginBox, 
    Title, 
    InputForm, 
    Input, 
    ButtonBox, 
    LoginButton,
    KakaoButton,
    JoinButton,
} from "../styles/pages/Login.styles";
import { ResponseBody } from '../types/api';
import { useNavigation } from '../hooks/useNavigation';



const Login = () => {
    const { handleNavigateToJoin, handleNavigateToMain } = useNavigation();

    const [nameInput, setNameInput] = useState<string>('');
    const [passwordInput, setPasswordInput] = useState<string>('');

    useEffect(function init() {
        localStorage.clear();
    }, []);

    const handleNameInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setNameInput(e.target.value);
    };

    const handlePasswordInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPasswordInput(e.target.value);
    };

    const handleLoginRequest = () => {
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

        fetch('http://localhost:8080/api/v1/auth/login', requestOptions)
            .then((response: Response) => {
                if(response.ok) {
                    alert(`[로그인 완료]`);
                    localStorage.setItem('Authorization', response.headers.get('Authorization')!);
                    handleNavigateToMain();
                    return;
                }

                response.json().then((responseBody: ResponseBody) => {
                    alert(`
                    [로그인 실패]
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
            <LoginBox>
                <Title>Login</Title>

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
                    <LoginButton onClick={handleLoginRequest}>login</LoginButton>
                    <KakaoButton>kakao Login</KakaoButton>
                    <JoinButton onClick={handleNavigateToJoin}>join</JoinButton>
                </ButtonBox>

            </LoginBox>

        </Container>
    )
}

export default Login;