import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

export const useNavigation = () => {
    const navigate = useNavigate();

    return {
        handleNavigateToLogin: useCallback(() => navigate('/'), [navigate]),
        handleNavigateToJoin: useCallback(() => navigate(`/join`), [navigate]),
        handleNavigateToMain: useCallback(() => navigate(`/main`), [navigate])
    };
};
