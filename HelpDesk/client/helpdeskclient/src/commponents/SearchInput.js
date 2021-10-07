import React from "react";

class SearchInput extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    return (
      <div class="container">
        <div class="w-25 my-3">
          <input
            name="search"
            class="form-control"
            type="search"
            onChange={this.props.onFilter}
        
          />
        </div>
      </div>
    );
  }
}
export default SearchInput
