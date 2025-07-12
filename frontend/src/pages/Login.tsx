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
import { useNavigation } from '../hooks/useNavigation';



const Login = () => {
    const { handleNavigateToJoin, handleNavigateToMain } = useNavigation();

    return (
        <Container>
            <LoginBox>
                <Title>Login</Title>

                <InputForm>
                    <Input 
                        placeholder="name"
                        type="text"
                    ></Input>
                    <Input 
                        placeholder="password"
                        type="password"
                    ></Input>
                </InputForm>

                <ButtonBox>
                    <LoginButton>login</LoginButton>
                    <KakaoButton>kakao Login</KakaoButton>
                    <JoinButton onClick={handleNavigateToJoin}>join</JoinButton>
                </ButtonBox>

            </LoginBox>

        </Container>
    )
}

export default Login;