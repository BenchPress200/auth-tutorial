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
        const requestOptions: RequestInit = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include',
        }

        fetch(`http://localhost:8080/api/v1/auth/logout`, requestOptions)
            .then((response: Response) => {
                if(response.ok) {
                    console.log('인증 쿠키 초기화')
                    return;
                }

            })
            .catch((error: Error) => {
                console.error(error.message);
            })
            .finally();
    }, []);

    useEffect(() => {
        const receiveMessage = (event: MessageEvent) => {
            if (event.origin !== 'http://localhost:3000') {
                console.warn('보안 경고: 알 수 없는 출처로부터의 메시지:', event.origin);
                return; 
            }

            if (event.data && event.data.type === 'oauthSuccess') {
                handleNavigateToMain(); 
            }
        };

        window.addEventListener('message', receiveMessage);

        return () => {
            window.removeEventListener('message', receiveMessage);
        };
    }, [handleNavigateToMain]); // handleNavigateToMain이 변경될 수 있다면 의존성 추가

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
            credentials: 'include',
        }

        fetch('http://localhost:8080/api/v1/auth/login', requestOptions)
            .then((response: Response) => {
                if(response.ok) {
                    alert(`[로그인 완료]`);
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

    const handleKakaoLoginRequest = () => {
        window.open(
            'http://localhost:8080/oauth2/authorization/kakao' +
            '?client_id=a782a86024836bfe8eae9b58dbcdd3b6' +
            '&redirect_uri=http://localhost:8080/login/oauth2/code/kakao' +
            '_blank',
        )
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
                    <KakaoButton onClick={handleKakaoLoginRequest}>kakao Login</KakaoButton>
                    <JoinButton onClick={handleNavigateToJoin}>join</JoinButton>
                </ButtonBox>

            </LoginBox>

        </Container>
    )
}

export default Login;