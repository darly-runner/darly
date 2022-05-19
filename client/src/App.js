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
import BasicButton from "./components/Button";

function App() {
  return (
    <div className="App">
      <Header>
        <Icon icon="darlyBig" width={130} height={50} viewBox="0 0 507 207" />
        <BasicButton width={100} height={50} fontSize="16px">
          <a href="https://darly-bucket.s3.ap-northeast-2.amazonaws.com/darly.apk">
            앱 다운로드
          </a>
          <Icon icon="download" width={24} height={24} viewBox="0 0 24 24" />
        </BasicButton>
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
