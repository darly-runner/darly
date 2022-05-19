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
import BasicButton from "./components/Button";

function App() {
  return (
    <div className="App">
      <Header>
        <Icon icon="darlyBig" width={507} height={207} viewBox="0 0 507 207" />
        <BasicButton width={400} height={25} fontSize="16px">
          앱 다운로드
          <Icon icon="download" width={24} height={24} viewBox="0 0 24 24" />
        </BasicButton>
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
