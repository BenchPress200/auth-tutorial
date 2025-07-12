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
import { useNavigation } from '../hooks/useNavigation';



const Join = () => {
    const { handleNavigateToLogin } = useNavigation();

    return (
        <Container>
            <JoinBox>
                <Title>Join</Title>
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
                    <JoinButton>join</JoinButton>
                    <LoginButton onClick={handleNavigateToLogin}>login</LoginButton>
                </ButtonBox>
            </JoinBox>
        

        </Container>
    )
}

export default Join;