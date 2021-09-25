import React from "react";

class Form extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const { onHandleChange } = this.props;
    return (
      <div>
        <div class="container w-50 my-5">
          <form>
            <div class="row my-3">
              <div class="col-4">
                <label>
                  Category<span class="text-danger">*</span>
                </label>
              </div>
              <div class="col-8">
                <select
                  name="category"
                  class="form-control w-75"
                  value={this.props.category}
                  onChange={onHandleChange}
                >
                  <option></option>
                  <option>Application & Services</option>
                  <option>Benefits & Paper Work</option>
                  <option>Hardware & Software</option>
                  <option>People Management</option>
                  <option>Security & Access</option>
                  <option>Workplaces & Facilities</option>
                </select>
              </div>
            </div>
            <div class="row my-3">
              <div class="col-4">
                <label>
                  Name<span class="text-danger">*</span>
                </label>
              </div>
              <div class="col-8">
                <input
                  name="name"
                  class="form-control w-75"
                  type="text"
                  value={this.props.name}
                  onChange={onHandleChange}
                />
              </div>
            </div>

            <div class="row my-3">
              <div class="col-4">
                <label>Description</label>
              </div>
              <div class="col-8">
                <textarea
                  name="description"
                  class="form-control"
                  rows="3"
                  value={this.props.description}
                  onChange={onHandleChange}
                />
              </div>
            </div>

            <div class="row my-3">
              <div class="col-4">
                <label>
                  Urgency<span class="text-danger">*</span>
                </label>
              </div>
              <div class="col-8">
                <select
                  name="urgency"
                  class="form-control w-75"
                  value={this.props.urgency}
                  onChange={onHandleChange}
                >
                  <option></option>
                  <option>CRITICAL</option>
                  <option>HIGH</option>
                  <option>AVERAGE</option>
                  <option>LOW</option>
                </select>
              </div>
            </div>

            <div class="row my-3">
              <div class="col-4">
                <label>Desired resolution date</label>
              </div>
              <div class="col-8">
                <input
                  name="desiredResolutionDate"
                  type="date"
                  class="form-control w-75"
                  min={new Date().toISOString().split("T")[0]}
                  value={this.props.desiredResolutionDate}
                  onChange={onHandleChange}
                />
              </div>
            </div>

            <div class="row my-3">
              <div class="col-4">
                <label>Add attachments</label>
              </div>
              <div class="col-8">
                <label class="btn btn-info">
                  Browser
                  <input
                    type="file"
                    multiple
                    id="input"
                    accept="application/pdf,application/msword,image/png,image/jpeg,image/pjpeg,
                application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                    hidden
                    onChange={this.props.onHandleChangeAttachment}
                  />
                </label>
              </div>
              <div>
                {this.props.attachments &&
                  this.props.attachments.map((a) => (
                    <div>
                      <a
                        href={
                          "/tickets/" +
                          this.props.ticketId +
                          "/attachments/" +
                          a.id
                        }
                        class="d-block"
                        target="_blank"
                      >
                        {a.name}
                      </a>
                      <button
                        className="btn btn-secondary outlined w-15"
                        type="button"
                        onClick={() => this.props.onDeleteFile(a.name)}
                      >
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                  ))}
              </div>
            </div>
            <div class="row my-3">
              <div class="col-4">
                <label>Comment</label>
              </div>
              <div class="col-8">
                <textarea
                  name="comment"
                  class="form-control"
                  rows="2"
                  value={this.props.comment}
                  onChange={onHandleChange}
                />
              </div>
            </div>
          </form>
        </div>
        <div className="container ">
          <div className="row">
            <div className="col-md-5"></div>
            <div className="col-md-2 my-4">
              <input
                class="btn btn-secondary w-75"
                type="button"
                value="Save as Draft"
                onClick={this.props.draftTicket}
                onHandleChange={onHandleChange}
              />
            </div>
            <div className="col-md-2 my-4">
              <input
                class="btn btn-success w-75"
                type="button"
                value="Submit"
                onClick={this.props.newTicket}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Form;
