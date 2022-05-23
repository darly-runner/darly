import "./App.css";
import {
  Activity,
  Compete,
  Crew,
  Download,
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
        <Download href="https://darly-bucket.s3.ap-northeast-2.amazonaws.com/darly.apk">
          <BasicButton width={110} height={50} fontSize="16px">
            앱 다운로드
            <Icon icon="download" width={24} height={24} viewBox="0 0 24 24" />
          </BasicButton>
        </Download>
        <Download href="">
          <BasicButton width={500} height="50" fontSize="16px">
            워치용 앱 다운로드
            <Icon icon="download" width={24} height={24} viewBox="0 0 24 24" />
          </BasicButton>
        </Download>
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
