import "./App.css";
import {
  Activity,
  ButtonBox,
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
import { useMediaQuery } from "react-responsive";
import { MainLayout } from "./AppMobile.styled";

function App() {
  // const isPc = useMediaQuery({
  //   query: "(min-width:1024px)",
  // });
  const isPc = useMediaQuery({
    query: "(min-width:768px)",
  });
  // const isTablet = useMediaQuery({
  //   query: "(min-width:768px) and (max-width:1023px)",
  // });
  const isMobile = useMediaQuery({
    query: "(max-width:767px)",
  });

  return (
    <div className="App">
      {isPc && (
        <>
          <Header>
            <Icon
              icon="darlyBig"
              width={130}
              height={50}
              viewBox="0 0 507 207"
            />
            <ButtonBox>
              <Download href="https://darly-bucket.s3.ap-northeast-2.amazonaws.com/darly.apk">
                <BasicButton width={110} height={50} fontSize="16px">
                  앱 다운로드
                  <Icon
                    icon="download"
                    width={24}
                    height={24}
                    viewBox="0 0 24 24"
                  />
                </BasicButton>
              </Download>
              <Download href="">
                <BasicButton width={500} height="50" fontSize="16px">
                  워치용 앱 다운로드
                  <Icon
                    icon="download"
                    width={24}
                    height={24}
                    viewBox="0 0 24 24"
                  />
                </BasicButton>
              </Download>
            </ButtonBox>
          </Header>

          <Layout>
            <Main></Main>
            <Crew></Crew>
            <Compete></Compete>
            <Activity></Activity>
            <Friends></Friends>
          </Layout>
        </>
      )}
      {isMobile && (
        <MainLayout>
          <Header>
            <Icon
              icon="darlyWhiteBig"
              width={130}
              height={50}
              viewBox="0 0 507 207"
            />
          </Header>
        </MainLayout>
      )}
    </div>
  );
}

export default App;
