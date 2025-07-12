import {
    Container,
    ContentBox,
    ResponseBox,
    ButtonBox,
    UserDetailsFetchButton,
    LogoutButton,
} from '../styles/pages/Main.styles';
import { useNavigation } from '../hooks/useNavigation';



const Main = () => {
    const { handleNavigateToJoin } = useNavigation();

    return (
        <Container>
            <ContentBox>
                <ResponseBox>
                    Request your profile !
                </ResponseBox>

                <ButtonBox>
                    <UserDetailsFetchButton>My Profile</UserDetailsFetchButton>
                    <LogoutButton>logout</LogoutButton>
                </ButtonBox>
            </ContentBox>

        </Container>
    )
}

export default Main;