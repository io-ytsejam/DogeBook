import MuiAlert, {AlertProps} from "@material-ui/lab/Alert";
import React from "react";

export default function Alert(props: AlertProps) {
  return <MuiAlert variant="filled" {...props} />;
}