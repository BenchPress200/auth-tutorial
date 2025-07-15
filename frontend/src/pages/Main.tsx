import {
    Container,
    ContentBox,
    ResponseBox,
    ButtonBox,
    UserDetailsFetchButton,
    LogoutButton,
    ResponseBoxItem,
    Title,
} from '../styles/pages/Main.styles';
import { useState, useEffect } from 'react';
import { ResponseBody } from '../types/api';
import { useNavigation } from '../hooks/useNavigation';



const Main = () => {
    const { handleNavigateToLogin } = useNavigation();

    const [userId, setUserId] = useState<number>(0);
    const [name, setName] = useState<string>('');
    const [provider, setProvider] = useState<string>('');


    useEffect(function init() {
        const requestOptions: RequestInit = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include',
        }

        fetch('http://localhost:8080/api/v1/auth/whoami', requestOptions)
            .then((response: Response) => {
                if(response.ok) {
                    response.json().then((responseBody: ResponseBody) => {
                        setUserId(responseBody.data.userId);
                    })    
                    
                    return;
                }

                response.json().then((data: ResponseBody) => {
                    alert(`
                    [id 조회 실패]
                    status: ${data.status}
                    message: ${data.message}
                    timestamp: ${data.timestamp}`
                    )
                    
                    handleNavigateToLogin();
                })
            })
            .catch((error: Error) => {
                console.error(error.message);
            })
            .finally();

    }, []);

    const handleUserDetailsRequest = () => {
        const requestOptions: RequestInit = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include',
        }

        fetch(`http://localhost:8080/api/v1/users/${userId}`, requestOptions)
            .then((response: Response) => {
                if(response.ok) {
                    response.json().then((responseBody: ResponseBody) => {
                        setName(responseBody.data.name);
                        setProvider(responseBody.data.provider);
                    })    
                    
                    return;
                }

            })
            .catch((error: Error) => {
                console.error(error.message);
            })
            .finally();
    }

    const handleLogoutRequest = () => {
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
                    alert('[로그아웃 성공]')
                    localStorage.clear();    
                    handleNavigateToLogin();
                    
                    return;
                }

            })
            .catch((error: Error) => {
                console.error(error.message);
            })
            .finally();
    }



    return (
        <Container>
            <ContentBox>
                <Title>Request your profile !</Title>
                <ResponseBox>
                    <ResponseBoxItem>ID: {userId}</ResponseBoxItem>
                    {name &&
                    <>
                        <ResponseBoxItem>NAME: {name}</ResponseBoxItem>
                        <ResponseBoxItem>PROVIDER: {provider}</ResponseBoxItem>
                    </>
                    }
                    
                </ResponseBox>
                

                <ButtonBox>
                    <UserDetailsFetchButton onClick={handleUserDetailsRequest}>My Profile</UserDetailsFetchButton>
                    <LogoutButton onClick={handleLogoutRequest}>logout</LogoutButton>
                </ButtonBox>
            </ContentBox>

        </Container>
    )
}

export default Main;