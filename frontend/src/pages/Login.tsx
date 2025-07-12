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



const Login = () => {
    return (
        <Container>
            <LoginBox>
                <Title>Login</Title>

                <InputForm>
                    <Input placeholder="name">
                    </Input>
                    <Input placeholder="password">
                    </Input>
                </InputForm>

                <ButtonBox>
                    <LoginButton>login</LoginButton>
                    <KakaoButton>kakao Login</KakaoButton>
                    <JoinButton>join</JoinButton>
                </ButtonBox>

            </LoginBox>

        </Container>
    )
}

export default Login;