import { useEffect } from 'react';

import { 
    Container,
 } from '../styles/pages/Oauth2Callback.styles';

const Oauth2Callback = () => {
    // 창 닫고 메인 페이지로
    useEffect(() => {
        if (window.opener) {
            window.opener.postMessage({ 
                type: 'oauthSuccess',  
            }, window.location.origin);

        } else {
            console.error("OAuth 콜백 오류: 부모 창이 없습니다.");
            
        }

        setTimeout(() => {
            console.log('팝업 창 닫기 시도...');
            window.close();
        }, 100); 
        alert('카카오 로그인 성공') 
    }, []);

    return (
        <Container>
        </Container>
    )
}

export default Oauth2Callback