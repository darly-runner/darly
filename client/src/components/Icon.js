import React from "react";
import { icons } from "_foundation";
import styled from "styled-components";

const Icon = ({ icon, block, state, ...props }) => {
  return (
    <Svg
      viewBox="0 0 27 27"
      width="27"
      height="27"
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      block={block}
      className={state}
      // stroke="currentColor"
      {...props}
    >
      {icons[icon]}
    </Svg>
  );
};

export default Icon;

const Svg = styled.svg`
  display: ${(props) => (props.block ? "block" : "inline-block")};
  vertical-align: middle;
  shape-rendering: inherit;
  transform: translate3d(0, 0, 0);
  stroke-width: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
`;
