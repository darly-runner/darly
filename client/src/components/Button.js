import React from "react";
import styled, { css } from "styled-components";

function BasicButton({ onClick, able, width, height, fontSize, children }) {
  return (
    <StyledBtn
      width={width}
      height={height}
      fontSize={fontSize}
      able={able}
      onClick={onClick}
    >
      {children}
    </StyledBtn>
  );
}
export default BasicButton;

const StyledBtn = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 140px;
  height: 48px;
  /* width: ${(props) => props.width};
  height: ${(props) => props.height}; */
  padding: 0.3rem 1.2rem;
  font-weight: 600;
  border-radius: 4rem;
  border: none;
  color: #f7f8fb;
  background-color: #fb5454;
  font-size: ${(props) => props.fontSize};
  /* line-height: 1.75rem; */
  text-align: center;
  text-decoration: none;
  transition: 0.2s;
  cursor: pointer;

  svg {
    width: 18px;
    height: 18px;
    margin-left: 6px;
    vertical-align: top;
  }
`;
