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

export const LoginBox = styled.div`
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

export const Title = styled.h1`
    margin: 0;

    font-size: 32px;
    font-weight: 700;
    color: black;
`

export const InputForm = styled.div`
    margin-top: 40px;
    width: 100%;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    gap: 10px;
`

export const Input = styled.input`
    width: calc(100% - 20px);
    padding: 10px;

    border: 1px solid #d1d5db;
    border-radius: 8px;
    transition: border-color 0.2s;

    font-size: 16px;
    line-height: 17px;

    &:focus {
        outline: none;
        border-color: #3b82f6;
        box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
    }

    &::placeholder {
        color: #9ca3af;
    }
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

export const LoginButton = styled.button`
    width: 100%;
    padding: 10px;
    background-color: #3b82f6;
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

export const KakaoButton = styled.button`
    width: 100%;
    padding: 10px;
    background-color: #fee500;
    color: #000;
    border: none;

    border-radius: 8px;

    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s;

    &:hover {
        background-color: #fde047;
    }

    &:active {
        transform: translateY(1px);
    }
`

export const JoinButton = styled.button`
    width: 100%;
    padding: 10px;
    background-color: transparent;
    color: #374151;
    border: 1px solid #d1d5db;

    border-radius: 8px;

    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s;

    &:hover {
        background-color: #f9fafb;
    }

    &:active {
        transform: translateY(1px);
    }
`