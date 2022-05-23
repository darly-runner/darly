import styled from "styled-components";

export const Header = styled.div`
  width: 100%;
  position: fixed;
  padding: 4vh 7vh;

  display: flex;
  justify-content: space-between;
  align-items: center;

  background-color: rgba(256, 256, 256, 0);

  img {
    width: 170px;
  }
  /* background-color: yellow; */
`;

export const ButtonBox = styled.div`
  display: flex;
  gap: 15px;
`;

export const Download = styled.a`
  text-decoration: none;
`;

export const Layout = styled.div`
  width: 100%;
  /* padding-top: 13vh; */
`;

export const Main = styled.div`
  width: 100%;
  /* height: 784px; */
  height: 100vh;
  /* background-color: #fff8f8; */
`;

export const Crew = styled.div`
  width: 100%;
  height: 784px;
  background-color: #fff8f8;
`;

export const Compete = styled.div`
  width: 100%;
  height: 784px;
  /* background-color: #fff8f8; */
`;

export const Activity = styled.div`
  width: 100%;
  height: 784px;
  background-color: #fff8f8;
`;

export const Friends = styled.div`
  width: 100%;
  height: 784px;
  /* background-color: #fff8f8; */
`;
