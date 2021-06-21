import Typography from "@material-ui/core/Typography";
import {
  Avatar,
  Box, Card,
  Container,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText, TextField,
  Tooltip
} from "@material-ui/core";
import React from "react";
import {getFontSizeForContent} from "../utils/getFontSizeForContent";
import Button from "@material-ui/core/Button";
import Snackbar from "@material-ui/core/Snackbar/Snackbar";
import {Alert} from "@material-ui/lab";
import CommentEditor from "./CommentEditor";

interface CommentsProps {
  dogePost: DogePost
  reloadDogePosts: () => void
}

export default function Comments ({ dogePost, reloadDogePosts }: CommentsProps) {
  const { comments } = dogePost

  return <>
    <List>
      {comments.map(comment => <Comment comment={comment}/>)}
    </List>
    <CommentEditor dogePost={dogePost} reloadDogePosts={reloadDogePosts} />
  </>
}


interface CommentProps {
  comment: Comment
}

function Comment({ comment }: CommentProps) {
  const { doge, content, creationTime } = comment
  const { username, firstname, lastname } = doge

  return <div>
    <Divider variant="inset" component="li" />
    <ListItem alignItems="flex-start">
      <Tooltip title={`@${username}`}>
        <ListItemAvatar>
          <Avatar alt="Remy Sharp" />
        </ListItemAvatar>
      </Tooltip>
      <ListItemText
        primary={`${firstname} ${lastname}`}
        secondary={
          <React.Fragment>
            <Typography
              component="span"
              variant="body2"
              color="textPrimary"
            >
              {content}
            </Typography>
          </React.Fragment>
        }
      />
    </ListItem>
  </div>
}