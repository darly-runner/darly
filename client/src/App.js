import logo from "./logo.svg";
import "./App.css";
import {
  Activity,
  Compete,
  Crew,
  Friends,
  Header,
  Layout,
  Main,
} from "./App.styled";
import Icon from "./components/Icon";

function App() {
  return (
    <div className="App">
      <Header>
        <Icon icon="darlyDefault" />
      </Header>
      <Layout>
        <Main></Main>
        <Crew></Crew>
        <Compete></Compete>
        <Activity></Activity>
        <Friends></Friends>
      </Layout>
    </div>
  );
}

export default App;
