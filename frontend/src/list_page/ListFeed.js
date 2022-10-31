import React, {useEffect, useState} from "react";
import ListButton from "./ListButton";
import AddListModal from "./AddListModal";
import Post from "../home_page/Post";
import "./list_css/ListFeed.css";
import {getLists} from "../services/api/lists/getLists";
import jsonPosts from "../data/posts.json";

function ListFeed() {
    const [userLists, setUserLists] = useState([]);
    const [posts, setPosts] = useState("hide");
    const [lists, setLists] = useState("show");
    const [postData, setPostData] = useState(jsonPosts);

    useEffect(() => {
        let mounted = true;
        const tokenString = localStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        getLists(userToken.token)
            .then(items => {
                if (mounted) {
                    console.log(items);
                    setUserLists(items)
                }
            })
        return () => mounted = false;
    }, [])




    return (
        <div className="listfeed">
            <div className="listfeed__header">
                <h2 id="showLists" onClick={() => {setPosts("hide"); setLists("show")}}>Lists</h2>
                <AddListModal />
            </div>

            {/*TODO: fill with list specific creator posts when we get posts assigned to creators */}
            <div className={posts}> {/* hide until a list is chosen, then do http request to get list posts */}
                {postData.map((item) => (
                    <Post
                        displayName={item.displayName}
                        username={item.username}
                        verified={item.verified}
                        text={item.text}
                        avatar={item.avatar}
                    />
                ))}
            </div>

            <div className={lists}>
                {userLists.map((item) => (
                    <ListButton
                        setPosts={setPosts}
                        setLists={setLists}
                        id={item.list_id}
                        title={item.title}
                        description={item.description}
                        added_creators={item.added_creators.toString().split(',')}
                    />
                ))}
            </div>
        </div>
    );
}

export default ListFeed;