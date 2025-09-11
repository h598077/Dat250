<script>
    let username = "";
    let question_ = "";
    let validUntil = "";
    let options = [];
    let selectedUser = "";

    let createusername="";
    let createemail="";

    let allusernames = [];
let errormesssage="";


function votepoll(index,question){
    if(selectedUser===""){

    }else{
        const result = fetch("http://localhost:8080/votes?username="+selectedUser+"&pollquestion="+question+"&optionindex="+index, {
            method: "POST",
        })
        window.location.reload(); //
    }

}


    function createPoll() {


        if(allusernames.includes(username) ||!(username ==="")){
            const result = fetch("http://localhost:8080/polls?username="+username+"&question="+question_+"&validuntil="+validUntil, {
                method: "POST",
                body: JSON.stringify(options),
                headers: {
                    "Content-Type": "application/json"
                },
            })
            window.location.reload();
        }else{
            errormesssage= "Username not found"
        }

    }


    function createUser(){
    if(allusernames.includes(createusername)|| createusername.length ===0){
        errormesssage= "Username is taken"
    }else{
        const result = fetch("http://localhost:8080/users?username="+createusername+"&email="+createemail, {
            method: "POST",

        })
        window.location.reload();
    }

    }


const resultuser = fetch("http://localhost:8080/users").then((response) =>response.json());
const result = fetch("http://localhost:8080/polls").then((response) =>response.json());
</script>
<h1>Questions:</h1>
{#await result}
It is loading...
{:then ready}
    {#each ready as poll}
        <p>  <b>{poll.question}</b> </p>
        <ul>
            {#each poll.voteoption as option,i}
                <li>{option.caption} <button on:click={() => votepoll(i,poll.question)}>Vote</button> Votes:{option.vote.length}</li>
            {/each}
        </ul>

        {/each}
{:catch error}
    {error}
{/await}





{#await resultuser}
    <p>Users loading...</p>
{:then ready}
    <select bind:value={selectedUser}>
        <option value="" disabled selected>Select a user</option>
        {#each ready as user}
            {@html (() => { allusernames = ready.map(u => u.username); return ''; })()}
            <option value={user.username}>{user.username}</option>
        {/each}
    </select>
    <p>Selected user: {selectedUser}</p>
{:catch error}
    <p>Error: {error}</p>
{/await}






<div>
<h1>Create polls:</h1>
    <label>
        Username:
        <input type="text" bind:value={username} placeholder="Enter your username" />
    </label>
    <br>
<label>
    Question:
    <input type="text" bind:value={question_} placeholder="Enter your question" />
</label>
<br>
<label>
    Valid Until (days):
    <input type="number" bind:value={validUntil} min="1" placeholder="Enter valid until"/>
</label>
<br>
    <p>Options</p>
    <input type="text" placeholder="Option 1" bind:value={options[0]} />
    <input type="text" placeholder="Option 2" bind:value={options[1]} />
    <input type="text" placeholder="Option 3" bind:value={options[2]} />
<button on:click={createPoll}>Create Poll</button>
</div>
<div>
    <h1>Create User:</h1>
    <input type="text" placeholder="Username" bind:value={createusername} />
    <input type="text" placeholder="Email" bind:value={createemail} />
    <button on:click={createUser}>Create User</button>
</div>

{errormesssage}