import React from "react";
import axios from "axios";
import FileSaver from "file-saver";

class AttachmentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      ticketId: this.props.match.params.ticketId,
      attachmentId: this.props.match.params.attachmentId,
      file: null,
      type: null,
      blob: null,
      attachment: [],
    };
  }
  componentDidMount() {
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.state.ticketId +
          "/attachments/" +
          this.state.attachmentId,
        JSON.parse(localStorage.AuthHeader)
      )
      .then((resp) => {
        this.setState({ attachment: resp.data });
        let type = this?.state.attachment.name.split(".");
        this.setState({ type: type[type.length - 1] });
        let blob = new Blob([this.state.attachment.blob], { type: type });
        this.setState({
          file: FileSaver.saveAs(blob, "file." + this.state.type),
        });
      });
  }

  onGetFileType(fileName) {
    let type;
    let arrayNameFileSplitDot = fileName.split(".");
    switch (arrayNameFileSplitDot[arrayNameFileSplitDot.length - 1]) {
      case "jpeg":
        type = "image/jpeg";
        break;
      case "jpg":
        type = "image/jpeg";
        break;
      case "png":
        type = "image/png";
        break;
      case "pdf":
        type = "application/pdf";
        break;
      case "docx":
        type =
          "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        break;
      case "doc":
        type = "application/msword";
        break;
    }
    return type;
  }

  render() {
    let type = this.state.type;
    console.log(type);
    switch (type) {
      case "jpeg":
        return <img width="100" height="100" src={this.state.file} />;
        break;
      case "jpg":
        return <img width="100" height="100" src={this.state.file} />;
        break;
      case "png":
        return <img width="100" height="100" src={this.state.file} />;
        break;
      case "pdf":
        window.open(this.state.file, "_self");
        return <span>Downloading...</span>;
      case "docx":
        window.open(this.state.file, "_self");
        return <span>Downloading...</span>;
      case "doc":
        window.open(this.state.file, "_self");
        return <span>Downloading...</span>;
      default:
        return "";
    }
  }
}
export default AttachmentContainer;
