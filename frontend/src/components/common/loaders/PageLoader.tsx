import {RingLoader} from "react-spinners";

function PageLoader() {
  return (
    <>
        <div className="h-[100dvh] w-[100%] bg-black flex justify-center items-center ">
            <RingLoader
                color="#d6a436"
                size={85}
            />
        </div>

    </>
  );
}

export default PageLoader;
