import styled from 'styled-components';

export const Container = styled.div`
    padding: 0;
    margin: 0;
    width: 100%;
    height: 100%;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`

export const ContentBox = styled.div`
    width: 400px;
    padding: 25px;

    display: flex;
    flex-direction: column;
    align-items: center;

    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
    border: 0.5px solid rgba(0, 0, 0, 0.1);
    box-sizing: border-box;
`

export const ResponseBox = styled.div`
    width: calc(100% - 20px);
    padding: 10px;

    border: 0.5px solid rgba(0, 0, 0, 0.3);
    border-radius: 12px;

    color: rgba(0, 0, 0, 0.5);
    font-size: 16px;
    font-weight: 600;
    line-height: 17px;
`

export const ButtonBox = styled.div`
    margin-top: 30px;
    width: 100%;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    gap: 10px;
`

export const UserDetailsFetchButton = styled.button`
    width: 100%;
    padding: 10px;
    background-color: #3b82f6;;
    color: white;
    border: none;

    border-radius: 8px;

    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s;

    &:hover {
        background-color: #2563eb;
    }

    &:active {
        transform: translateY(1px);
    }
`


export const LogoutButton = styled.button`
    width: 100%;
    padding: 10px;
    background-color: #ff6347;
    color: white;
    border: none;

    border-radius: 8px;

    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s;

    &:hover {
        background-color: #e5533d;
    }

    &:active {
        transform: translateY(1px);
    }
`
