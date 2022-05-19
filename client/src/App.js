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
  Title,
} from "./App.styled";
import Icon from "./components/Icon";

function App() {
  return (
    <div className="App">
      <Header>
        <Icon icon="darlyBig" width={507} height={207} viewBox="0 0 507 207" />
        {/* <Title>D</Title> */}
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
