import logo from "./logo.svg";
import "./App.css";
import { Activity, Compete, Crew, Friends, Header, Main } from "./App.styled";

function App() {
  return (
    <div className="App">
      <Header></Header>
      <Main></Main>
      <Crew></Crew>
      <Compete></Compete>
      <Activity></Activity>
      <Friends></Friends>
    </div>
  );
}

export default App;
