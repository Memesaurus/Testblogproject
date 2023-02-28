import React, { useEffect, useState } from "react";
import { Toast, ToastBody, ToastHeader } from "react-bootstrap";
import ReactDOM from "react-dom";

type Props = {
  isError?: boolean;
  alertType: "dark" | "danger" | "warning";
  body: string;
};

function GeneralUseToast(props: Props): JSX.Element {
  const [isRendered, setIsRendered] = useState<boolean>(false);
  const [isShown, setIsShown] = useState<boolean>(true);

  useEffect(() => {
    setIsRendered(true);
  }, []);

  return (isRendered &&
    ReactDOM.createPortal(
      <Toast
        bg={props.alertType}
        show={isShown}
        onClose={() => setIsShown(!isShown)}
        delay={3000}
        autohide
      >
        <ToastHeader>
          {props.isError ? <strong>Error!</strong> : <strong>Success!</strong>}
        </ToastHeader>
        <ToastBody>{props.body}</ToastBody>
      </Toast>,
      document.getElementById("toaster_portal")!
    )) as JSX.Element;
}

export default GeneralUseToast;
