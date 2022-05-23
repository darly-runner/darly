import styled, { css } from "styled-components";

export const MainLayout = styled.div`
  background-color: #120c32;
  height: 100vh;
`;

export const MobileHeader = styled.div`
  font-family: tvNMedium;
  /* color: #fb5454; */
  padding: 20vh 0;
  /* padding-left: 4vw; */
  /* font-size: 80px; */

  color: #f7f8fb;
  font-size: 36px;

  svg {
    /* padding-left: 22vw; */
    width: 280px;
    /* height: 0px; */
  }

  div {
    color: #f7f8fb;
    margin: -24px;
    font-size: 36px;
  }

  img {
    width: 280px;
  }
`;

export const MobileButtonBox = styled.div`
  /* padding: 20vh; */
  display: grid;
  gap: 24px;
  justify-content: center;
  justify-items: center;

  button {
    height: 60px;
    width: 330px;
  }
`;
