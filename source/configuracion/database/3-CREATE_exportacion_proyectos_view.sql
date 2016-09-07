<!DOCTYPE html>
<html lang='en'>
<head>
<meta charset='utf-8'>
<meta content='IE=edge' http-equiv='X-UA-Compatible'>
<meta content='GitLab Community Edition' name='description'>
<title>configuracion/database/3-CREATE_exportacion_proyectos_view.sql | develop | proyectosBA / back-end | GitLab</title>
<link href="/assets/favicon-5738a6efe01f3282080df5f467da72a9.ico" rel="shortcut icon" type="image/vnd.microsoft.icon" />
<link href="/assets/application-a81ca6d11d79fbf86ca08c8f1359de4d.css" media="all" rel="stylesheet" />
<link href="/assets/print-47fe51cdf70398e5e54b544e0f5cc718.css" media="print" rel="stylesheet" />
<script src="/assets/application-30bdeb208dee0386a7a7298371bab417.js"></script>
<meta content="authenticity_token" name="csrf-param" />
<meta content="FBUnHx5fCeMVaMieKaFJRRk9r6oiTD7YrmQNYAZzpWU=" name="csrf-token" />
<script type="text/javascript">
//<![CDATA[
window.gon={};gon.default_issues_tracker="gitlab";gon.api_version="v3";gon.relative_url_root="";gon.default_avatar_url="http://gitlab.hexacta.com/assets/no_avatar-0b64d25ac5f63e6f0caee99e819105ba.png";gon.max_file_size=10;gon.current_user_id=58;gon.api_token="yCSCAgseTviq5ovh1yrJ";
//]]>
</script>
<meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
<meta content='#474D57' name='theme-color'>
<link href="/assets/touch-icon-iphone-4c0496ac9f88e7961644c66b289a6614.png" rel="apple-touch-icon" type="image/vnd.microsoft.icon" />
<link href="/assets/touch-icon-ipad-d64abe7a8e8be6e02245d2ed79da6ff9.png" rel="apple-touch-icon" sizes="76x76" type="image/vnd.microsoft.icon" />
<link href="/assets/touch-icon-iphone-retina-54af16810ab006406b4df81f8a92fe8d.png" rel="apple-touch-icon" sizes="120x120" type="image/vnd.microsoft.icon" />
<link href="/assets/touch-icon-ipad-retina-72774a950890a9e3811ab91f186cc11f.png" rel="apple-touch-icon" sizes="152x152" type="image/vnd.microsoft.icon" />
<meta content='/assets/msapplication-tile-0cf7066c37c04d40809fdcc2bf53a754.png' name='msapplication-TileImage'>
<meta content='#30353E' name='msapplication-TileColor'>




<style>
  [data-user-is] {
    display: none !important;
  }
  
  [data-user-is="58"] {
    display: block !important;
  }
  
  [data-user-is="58"][data-display="inline"] {
    display: inline !important;
  }
  
  [data-user-is-not] {
    display: block !important;
  }
  
  [data-user-is-not][data-display="inline"] {
    display: inline !important;
  }
  
  [data-user-is-not="58"] {
    display: none !important;
  }
</style>

</head>

<body class='ui_charcoal' data-page='projects:blob:show'>
<script>
  window.project_uploads_path = "/proyectosBA/back-end/uploads";
  window.markdown_preview_path = "/proyectosBA/back-end/markdown_preview";
</script>

<header class='header-expanded navbar navbar-fixed-top navbar-gitlab'>
<div class='container'>
<div class='header-logo'>
<a class="home" data-placement="bottom" data-toggle="tooltip" href="/" id="js-shortcuts-home" title="Dashboard"><img alt="Logo" src="/assets/logo-ca207549507c811d027110077cf86e90.svg" />
<div class='gitlab-text-container'>
<h3>GitLab</h3>
</div>
</a></div>
<div class='header-content'>
<button class='navbar-toggle' type='button'>
<span class='sr-only'>Toggle navigation</span>
<i class="fa fa-bars"></i>
</button>
<div class='navbar-collapse collapse'>
<ul class='nav navbar-nav pull-right'>
<li class='hidden-sm hidden-xs'>
<div class='search'>
<form accept-charset="UTF-8" action="/search" class="navbar-form pull-left" method="get"><div style="display:none"><input name="utf8" type="hidden" value="&#x2713;" /></div>
<input class="search-input form-control" id="search" name="search" placeholder="Search in this project" type="search" />
<input id="group_id" name="group_id" type="hidden" />
<input id="project_id" name="project_id" type="hidden" value="69" />
<input id="search_code" name="search_code" type="hidden" value="true" />
<input id="repository_ref" name="repository_ref" type="hidden" value="develop" />

<div class='search-autocomplete-opts hide' data-autocomplete-path='/search/autocomplete' data-autocomplete-project-id='69' data-autocomplete-project-ref='develop'></div>
</form>

</div>
<script>
  $('.search-input').on('keyup', function(e) {
    if (e.keyCode == 27) {
      $('.search-input').blur()
    }
  })
</script>

</li>
<li class='visible-sm visible-xs'>
<a data-placement="bottom" data-toggle="tooltip" href="/search" title="Search"><i class="fa fa-search"></i>
</a></li>
<li class='hidden-xs'>
<a data-placement="bottom" data-toggle="tooltip" href="/help" title="Help"><i class="fa fa-question-circle fa-fw"></i>
</a></li>
<li>
<a data-placement="bottom" data-toggle="tooltip" href="/explore" title="Explore"><i class="fa fa-globe fa-fw"></i>
</a></li>
<li>
<a data-placement="bottom" data-toggle="tooltip" href="/s/eselman" title="Your snippets"><i class="fa fa-clipboard fa-fw"></i>
</a></li>
<li class='hidden-xs'>
<a data-placement="bottom" data-toggle="tooltip" href="/projects/new" title="New project"><i class="fa fa-plus fa-fw"></i>
</a></li>
<li>
<a data-placement="bottom" data-toggle="tooltip" href="/profile" title="Profile settings"><i class="fa fa-cog fa-fw"></i>
</a></li>
<li>
<a class="logout" data-method="delete" data-placement="bottom" data-toggle="tooltip" href="/users/sign_out" rel="nofollow" title="Sign out"><i class="fa fa-sign-out"></i>
</a></li>
</ul>
</div>
<h1 class='title'><span><a href="/groups/proyectosBA">proyectosBA</a> / <a href="/proyectosBA/back-end">back-end</a></span></h1>
</div>
</div>
</header>


<div class='page-sidebar-expanded page-with-sidebar'>

<div class='sidebar-wrapper nicescroll'>
<ul class='nav nav-sidebar'>
<li class=""><a class="back-link" data-placement="right" href="/groups/proyectosBA" title="Back to group"><i class="fa fa-caret-square-o-left fa-fw"></i>
<span>
Back to Group
</span>
</a></li><li class='separate-item'></li>
<li class="home"><a class="shortcuts-project" data-placement="right" href="/proyectosBA/back-end" title="Project"><i class="fa fa-home fa-fw"></i>
<span>
Project
</span>
</a></li><li class=""><a class="shortcuts-project-activity" data-placement="right" href="/proyectosBA/back-end/activity" title="Project Activity"><i class="fa fa-dashboard fa-fw"></i>
<span>
Activity
</span>
</a></li><li class="active"><a class="shortcuts-tree" data-placement="right" href="/proyectosBA/back-end/tree/develop" title="Files"><i class="fa fa-files-o fa-fw"></i>
<span>
Files
</span>
</a></li><li class=""><a class="shortcuts-commits" data-placement="right" href="/proyectosBA/back-end/commits/develop" title="Commits"><i class="fa fa-history fa-fw"></i>
<span>
Commits
</span>
</a></li><li class=""><a class="shortcuts-network" data-placement="right" href="/proyectosBA/back-end/network/develop" title="Network"><i class="fa fa-code-fork fa-fw"></i>
<span>
Network
</span>
</a></li><li class=""><a class="shortcuts-graphs" data-placement="right" href="/proyectosBA/back-end/graphs/develop" title="Graphs"><i class="fa fa-area-chart fa-fw"></i>
<span>
Graphs
</span>
</a></li><li class=""><a data-placement="right" href="/proyectosBA/back-end/milestones" title="Milestones"><i class="fa fa-clock-o fa-fw"></i>
<span>
Milestones
</span>
</a></li><li class=""><a class="shortcuts-issues" data-placement="right" href="/proyectosBA/back-end/issues" title="Issues"><i class="fa fa-exclamation-circle fa-fw"></i>
<span>
Issues
<span class='count issue_counter'>0</span>
</span>
</a></li><li class=""><a class="shortcuts-merge_requests" data-placement="right" href="/proyectosBA/back-end/merge_requests" title="Merge Requests"><i class="fa fa-tasks fa-fw"></i>
<span>
Merge Requests
<span class='count merge_counter'>0</span>
</span>
</a></li><li class=""><a class="team-tab tab" data-placement="right" href="/proyectosBA/back-end/project_members" title="Members"><i class="fa fa-users fa-fw"></i>
<span>
Members
</span>
</a></li><li class=""><a data-placement="right" href="/proyectosBA/back-end/labels" title="Labels"><i class="fa fa-tags fa-fw"></i>
<span>
Labels
</span>
</a></li><li class=""><a class="shortcuts-wiki" data-placement="right" href="/proyectosBA/back-end/wikis/home" title="Wiki"><i class="fa fa-book fa-fw"></i>
<span>
Wiki
</span>
</a></li><li class="separate-item"><a data-placement="right" href="/proyectosBA/back-end/edit" title="Settings"><i class="fa fa-cogs fa-fw"></i>
<span>
Settings
</span>
</a></li></ul>

<div class='collapse-nav'>
<a class="toggle-nav-collapse" href="#" title="Open/Close"><i class="fa fa-angle-left"></i></a>

</div>
<a class="sidebar-user" href="/u/eselman"><img alt="User activity" class="avatar avatar s32" src="http://gitlab.hexacta.com/uploads/user/avatar/58/tractor_recortada.jpg" />
<div class='username'>
eselman
</div>
</a></div>
<div class='content-wrapper'>
<div class='container-fluid'>
<div class='content'>
<div class='flash-container'>
</div>

<div class='clearfix'>
<div class='hidden-xs center'>
<div class='slead'>
<span>You pushed to</span>
<a href="/proyectosBA/back-end/commits/candiateRelease"><strong>candiateRelease</strong>
</a>branch
<time class="time_ago js-timeago" data-placement="top" data-toggle="tooltip" datetime="2016-09-07T21:10:01Z" title="Sep 07, 2016 9:10pm">2016-09-07 21:10:01 UTC</time><script>
//<![CDATA[
$('.js-timeago').timeago()
//]]>
</script>
</div>
<div>
<a class="btn btn-info btn-sm" href="/proyectosBA/back-end/merge_requests/new?merge_request%5Bsource_branch%5D=candiateRelease&amp;merge_request%5Bsource_project_id%5D=69&amp;merge_request%5Btarget_branch%5D=develop&amp;merge_request%5Btarget_project_id%5D=69" title="New Merge Request">Create Merge Request
</a></div>
<hr>
</div>

<div class='tree-ref-holder'>
<form accept-charset="UTF-8" action="/proyectosBA/back-end/refs/switch" class="project-refs-form" method="get"><div style="display:none"><input name="utf8" type="hidden" value="&#x2713;" /></div>
<select class="project-refs-select select2 select2-sm" id="ref" name="ref"><optgroup label="Branches"><option value="AMQ-HTTP">AMQ-HTTP</option>
<option value="LDAP_usuario">LDAP_usuario</option>
<option value="Mock_authentication_adapter">Mock_authentication_adapter</option>
<option value="Tests">Tests</option>
<option value="candiateRelease">candiateRelease</option>
<option value="configuraciones">configuraciones</option>
<option value="crud-jurisdiccion">crud-jurisdiccion</option>
<option value="crud-proyecto">crud-proyecto</option>
<option selected="selected" value="develop">develop</option>
<option value="envio_permisos_a_FE">envio_permisos_a_FE</option>
<option value="exportacion_proyectos">exportacion_proyectos</option>
<option value="fix_archivos">fix_archivos</option>
<option value="fix_get_by_codigo">fix_get_by_codigo</option>
<option value="fix_tests">fix_tests</option>
<option value="generando_codigos">generando_codigos</option>
<option value="geocoding">geocoding</option>
<option value="implementacion_entidad_area">implementacion_entidad_area</option>
<option value="importador">importador</option>
<option value="jackson">jackson</option>
<option value="master">master</option>
<option value="objetivo_jurisdiccional-crud">objetivo_jurisdiccional-crud</option>
<option value="options_en_cors">options_en_cors</option>
<option value="resultado_importacion">resultado_importacion</option>
<option value="update_sql">update_sql</option></optgroup><optgroup label="Tags"></optgroup></select>
<input id="destination" name="destination" type="hidden" value="blob" />
<input id="path" name="path" type="hidden" value="configuracion/database/3-CREATE_exportacion_proyectos_view.sql" />
</form>


</div>
<div class='tree-holder' id='tree-holder'>
<ul class='breadcrumb repo-breadcrumb'>
<li>
<i class='fa fa-angle-right'></i>
<a href="/proyectosBA/back-end/tree/develop">back-end
</a></li>
<li>
<a href="/proyectosBA/back-end/tree/develop/configuracion">configuracion</a>
</li>
<li>
<a href="/proyectosBA/back-end/tree/develop/configuracion/database">database</a>
</li>
<li>
<a href="/proyectosBA/back-end/blob/develop/configuracion/database/3-CREATE_exportacion_proyectos_view.sql"><strong>
3-CREATE_exportacion_proyectos_view.sql
</strong>
</a></li>
</ul>
<ul class='blob-commit-info well hidden-xs'>
<li class='commit js-toggle-container'>
<div class='commit-row-title'>
<strong class='str-truncated'>
<a class="commit-row-message" href="/proyectosBA/back-end/commit/aceac75ebc53eaf30f16cae4c451427de6e427cd">Change Ciew script.</a>
</strong>
<div class='pull-right'>
<a class="commit_short_id" href="/proyectosBA/back-end/commit/aceac75ebc53eaf30f16cae4c451427de6e427cd">aceac75e</a>
</div>
<div class='notes_count'>
</div>
</div>
<div class='commit-row-info'>
<a class="commit-author-link has_tooltip" data-original-title="nemesys80@gmail.com" href="mailto:nemesys80@gmail.com"><img alt="" class="avatar s24" src="http://www.gravatar.com/avatar/fe019246647cf76d378931d58d8bc5cf?s=24&amp;d=identicon" width="24" /> <span class="commit-author-name">Nemesys</span></a>
authored
<div class='committed_ago'>
<time class="time_ago js-timeago" data-placement="top" data-toggle="tooltip" datetime="2016-09-07T19:51:46Z" title="Sep 07, 2016 7:51pm">2016-09-07 16:51:46 -0300</time> &nbsp;
</div>
<a class="pull-right" href="/proyectosBA/back-end/tree/aceac75ebc53eaf30f16cae4c451427de6e427cd">Browse Code »</a>
</div>
</li>

</ul>
<div class='tree-content-holder' id='tree-content-holder'>
<article class='file-holder'>
<div class='file-title'>
<i class="fa fa-file-text-o fa-fw"></i>
<strong>
3-CREATE_exportacion_proyectos_view.sql
</strong>
<small>
3.39 KB
</small>
<div class='file-actions hidden-xs'>
<div class='btn-group tree-btn-group'>
<a class="btn btn-small" href="/proyectosBA/back-end/edit/develop/configuracion/database/3-CREATE_exportacion_proyectos_view.sql">Edit</a>
<a class="btn btn-sm" href="/proyectosBA/back-end/raw/develop/configuracion/database/3-CREATE_exportacion_proyectos_view.sql" target="_blank">Raw</a>
<a class="btn btn-sm" href="/proyectosBA/back-end/blame/develop/configuracion/database/3-CREATE_exportacion_proyectos_view.sql">Blame</a>
<a class="btn btn-sm" href="/proyectosBA/back-end/commits/develop/configuracion/database/3-CREATE_exportacion_proyectos_view.sql">History</a>
<a class="btn btn-sm" href="/proyectosBA/back-end/blob/3955ae6a31c3b2abf7e0be6de5b46e8115e2d79d/configuracion/database/3-CREATE_exportacion_proyectos_view.sql">Permalink</a>
</div>
<button class="remove-blob btn btn-sm btn-remove" data-target="#modal-remove-blob" data-toggle="modal" name="button" type="submit">Remove
</button>
</div>
</div>
<div class='file-content code'>
<div class='code file-content white'>
<div class='line-numbers'>
<a data-line-number='1' href='#L1' id='L1'>
<i class='fa fa-link'></i>
1
</a>
<a data-line-number='2' href='#L2' id='L2'>
<i class='fa fa-link'></i>
2
</a>
<a data-line-number='3' href='#L3' id='L3'>
<i class='fa fa-link'></i>
3
</a>
<a data-line-number='4' href='#L4' id='L4'>
<i class='fa fa-link'></i>
4
</a>
<a data-line-number='5' href='#L5' id='L5'>
<i class='fa fa-link'></i>
5
</a>
<a data-line-number='6' href='#L6' id='L6'>
<i class='fa fa-link'></i>
6
</a>
<a data-line-number='7' href='#L7' id='L7'>
<i class='fa fa-link'></i>
7
</a>
<a data-line-number='8' href='#L8' id='L8'>
<i class='fa fa-link'></i>
8
</a>
<a data-line-number='9' href='#L9' id='L9'>
<i class='fa fa-link'></i>
9
</a>
<a data-line-number='10' href='#L10' id='L10'>
<i class='fa fa-link'></i>
10
</a>
<a data-line-number='11' href='#L11' id='L11'>
<i class='fa fa-link'></i>
11
</a>
<a data-line-number='12' href='#L12' id='L12'>
<i class='fa fa-link'></i>
12
</a>
<a data-line-number='13' href='#L13' id='L13'>
<i class='fa fa-link'></i>
13
</a>
<a data-line-number='14' href='#L14' id='L14'>
<i class='fa fa-link'></i>
14
</a>
<a data-line-number='15' href='#L15' id='L15'>
<i class='fa fa-link'></i>
15
</a>
<a data-line-number='16' href='#L16' id='L16'>
<i class='fa fa-link'></i>
16
</a>
<a data-line-number='17' href='#L17' id='L17'>
<i class='fa fa-link'></i>
17
</a>
<a data-line-number='18' href='#L18' id='L18'>
<i class='fa fa-link'></i>
18
</a>
<a data-line-number='19' href='#L19' id='L19'>
<i class='fa fa-link'></i>
19
</a>
<a data-line-number='20' href='#L20' id='L20'>
<i class='fa fa-link'></i>
20
</a>
<a data-line-number='21' href='#L21' id='L21'>
<i class='fa fa-link'></i>
21
</a>
<a data-line-number='22' href='#L22' id='L22'>
<i class='fa fa-link'></i>
22
</a>
<a data-line-number='23' href='#L23' id='L23'>
<i class='fa fa-link'></i>
23
</a>
<a data-line-number='24' href='#L24' id='L24'>
<i class='fa fa-link'></i>
24
</a>
<a data-line-number='25' href='#L25' id='L25'>
<i class='fa fa-link'></i>
25
</a>
<a data-line-number='26' href='#L26' id='L26'>
<i class='fa fa-link'></i>
26
</a>
<a data-line-number='27' href='#L27' id='L27'>
<i class='fa fa-link'></i>
27
</a>
<a data-line-number='28' href='#L28' id='L28'>
<i class='fa fa-link'></i>
28
</a>
<a data-line-number='29' href='#L29' id='L29'>
<i class='fa fa-link'></i>
29
</a>
<a data-line-number='30' href='#L30' id='L30'>
<i class='fa fa-link'></i>
30
</a>
<a data-line-number='31' href='#L31' id='L31'>
<i class='fa fa-link'></i>
31
</a>
<a data-line-number='32' href='#L32' id='L32'>
<i class='fa fa-link'></i>
32
</a>
<a data-line-number='33' href='#L33' id='L33'>
<i class='fa fa-link'></i>
33
</a>
<a data-line-number='34' href='#L34' id='L34'>
<i class='fa fa-link'></i>
34
</a>
<a data-line-number='35' href='#L35' id='L35'>
<i class='fa fa-link'></i>
35
</a>
<a data-line-number='36' href='#L36' id='L36'>
<i class='fa fa-link'></i>
36
</a>
<a data-line-number='37' href='#L37' id='L37'>
<i class='fa fa-link'></i>
37
</a>
<a data-line-number='38' href='#L38' id='L38'>
<i class='fa fa-link'></i>
38
</a>
<a data-line-number='39' href='#L39' id='L39'>
<i class='fa fa-link'></i>
39
</a>
<a data-line-number='40' href='#L40' id='L40'>
<i class='fa fa-link'></i>
40
</a>
<a data-line-number='41' href='#L41' id='L41'>
<i class='fa fa-link'></i>
41
</a>
<a data-line-number='42' href='#L42' id='L42'>
<i class='fa fa-link'></i>
42
</a>
<a data-line-number='43' href='#L43' id='L43'>
<i class='fa fa-link'></i>
43
</a>
<a data-line-number='44' href='#L44' id='L44'>
<i class='fa fa-link'></i>
44
</a>
<a data-line-number='45' href='#L45' id='L45'>
<i class='fa fa-link'></i>
45
</a>
<a data-line-number='46' href='#L46' id='L46'>
<i class='fa fa-link'></i>
46
</a>
<a data-line-number='47' href='#L47' id='L47'>
<i class='fa fa-link'></i>
47
</a>
<a data-line-number='48' href='#L48' id='L48'>
<i class='fa fa-link'></i>
48
</a>
<a data-line-number='49' href='#L49' id='L49'>
<i class='fa fa-link'></i>
49
</a>
<a data-line-number='50' href='#L50' id='L50'>
<i class='fa fa-link'></i>
50
</a>
<a data-line-number='51' href='#L51' id='L51'>
<i class='fa fa-link'></i>
51
</a>
<a data-line-number='52' href='#L52' id='L52'>
<i class='fa fa-link'></i>
52
</a>
<a data-line-number='53' href='#L53' id='L53'>
<i class='fa fa-link'></i>
53
</a>
<a data-line-number='54' href='#L54' id='L54'>
<i class='fa fa-link'></i>
54
</a>
<a data-line-number='55' href='#L55' id='L55'>
<i class='fa fa-link'></i>
55
</a>
<a data-line-number='56' href='#L56' id='L56'>
<i class='fa fa-link'></i>
56
</a>
<a data-line-number='57' href='#L57' id='L57'>
<i class='fa fa-link'></i>
57
</a>
</div>
<pre class="code highlight"><code><span id="LC1" class="line"><span class="c1">-- --------------------------------------------------------</span></span>&#x000A;<span id="LC2" class="line"><span class="c1">-- Host:                         10.140.150.154</span></span>&#x000A;<span id="LC3" class="line"><span class="c1">-- Server version:               10.1.14-MariaDB - mariadb.org binary distribution</span></span>&#x000A;<span id="LC4" class="line"><span class="c1">-- Server OS:                    Win64</span></span>&#x000A;<span id="LC5" class="line"><span class="c1">-- HeidiSQL Version:             9.3.0.4984</span></span>&#x000A;<span id="LC6" class="line"><span class="c1">-- --------------------------------------------------------</span></span>&#x000A;<span id="LC7" class="line"></span>&#x000A;<span id="LC8" class="line"><span class="cm">/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */</span><span class="p">;</span></span>&#x000A;<span id="LC9" class="line"><span class="cm">/*!40101 SET NAMES utf8mb4 */</span><span class="p">;</span></span>&#x000A;<span id="LC10" class="line"><span class="cm">/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */</span><span class="p">;</span></span>&#x000A;<span id="LC11" class="line"><span class="cm">/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=&#39;NO_AUTO_VALUE_ON_ZERO&#39; */</span><span class="p">;</span></span>&#x000A;<span id="LC12" class="line"></span>&#x000A;<span id="LC13" class="line"><span class="c1">-- Dumping structure for view proyectos_ba_generated.exportacion_proyectos_view</span></span>&#x000A;<span id="LC14" class="line"><span class="c1">-- Creating temporary table to overcome VIEW dependency errors</span></span>&#x000A;<span id="LC15" class="line"><span class="k">CREATE</span> <span class="k">TABLE</span> <span class="nv">`exportacion_proyectos_view`</span> <span class="p">(</span></span>&#x000A;<span id="LC16" class="line">	<span class="nv">`idProyecto`</span> <span class="n">INT</span><span class="p">(</span><span class="mi">10</span><span class="p">)</span> <span class="k">NOT</span> <span class="k">NULL</span><span class="p">,</span></span>&#x000A;<span id="LC17" class="line">	<span class="nv">`nombreJurisidiccion`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">512</span><span class="p">)</span> <span class="k">NOT</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC18" class="line">	<span class="nv">`nombreProyecto`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">512</span><span class="p">)</span> <span class="k">NOT</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC19" class="line">	<span class="nv">`estado`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">50</span><span class="p">)</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC20" class="line">	<span class="nv">`objetivoEstrategico`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">512</span><span class="p">)</span> <span class="k">NOT</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC21" class="line">	<span class="nv">`objetivoOperativo`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">512</span><span class="p">)</span> <span class="k">NOT</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC22" class="line">	<span class="nv">`descripcionProyecto`</span> <span class="n">TEXT</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC23" class="line">	<span class="nv">`responsable`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">512</span><span class="p">)</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC24" class="line">	<span class="nv">`area`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">256</span><span class="p">)</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC25" class="line">	<span class="nv">`organismosCorresponsables`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">512</span><span class="p">)</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC26" class="line">	<span class="nv">`fechaInicio`</span> <span class="n">DATE</span> <span class="k">NOT</span> <span class="k">NULL</span><span class="p">,</span></span>&#x000A;<span id="LC27" class="line">	<span class="nv">`fechaFin`</span> <span class="n">DATE</span> <span class="k">NULL</span><span class="p">,</span></span>&#x000A;<span id="LC28" class="line">	<span class="nv">`tipoProyecto`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">11</span><span class="p">)</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC29" class="line">	<span class="nv">`implicaCambioLegislativo`</span> <span class="n">TINYINT</span><span class="p">(</span><span class="mi">4</span><span class="p">)</span> <span class="k">NULL</span><span class="p">,</span></span>&#x000A;<span id="LC30" class="line">	<span class="nv">`prioridadJurisdiccional`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">50</span><span class="p">)</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC31" class="line">	<span class="nv">`meta`</span> <span class="n">DECIMAL</span><span class="p">(</span><span class="mi">22</span><span class="p">,</span><span class="mi">2</span><span class="p">)</span> <span class="k">NULL</span><span class="p">,</span></span>&#x000A;<span id="LC32" class="line">	<span class="nv">`unidadMeta`</span> <span class="n">VARCHAR</span><span class="p">(</span><span class="mi">512</span><span class="p">)</span> <span class="k">NULL</span> <span class="k">COLLATE</span> <span class="s1">&#39;latin1_swedish_ci&#39;</span><span class="p">,</span></span>&#x000A;<span id="LC33" class="line">	<span class="nv">`cantidadPoblacionImpactada`</span> <span class="n">BIGINT</span><span class="p">(</span><span class="mi">20</span><span class="p">)</span> <span class="k">NULL</span><span class="p">,</span></span>&#x000A;<span id="LC34" class="line">	<span class="nv">`prioridadJefatura`</span> <span class="n">BINARY</span><span class="p">(</span><span class="mi">0</span><span class="p">)</span> <span class="k">NULL</span><span class="p">,</span></span>&#x000A;<span id="LC35" class="line">	<span class="nv">`estadoAprobacion`</span> <span class="n">BINARY</span><span class="p">(</span><span class="mi">0</span><span class="p">)</span> <span class="k">NULL</span></span>&#x000A;<span id="LC36" class="line"><span class="p">)</span> <span class="n">ENGINE</span><span class="o">=</span><span class="n">MyISAM</span><span class="p">;</span></span>&#x000A;<span id="LC37" class="line"></span>&#x000A;<span id="LC38" class="line"></span>&#x000A;<span id="LC39" class="line"><span class="c1">-- Dumping structure for view proyectos_ba_generated.exportacion_proyectos_view</span></span>&#x000A;<span id="LC40" class="line"><span class="c1">-- Removing temporary table and create final VIEW structure</span></span>&#x000A;<span id="LC41" class="line"><span class="k">DROP</span> <span class="k">TABLE</span> <span class="n">IF</span> <span class="k">EXISTS</span> <span class="nv">`exportacion_proyectos_view`</span><span class="p">;</span></span>&#x000A;<span id="LC42" class="line"><span class="k">CREATE</span> <span class="n">ALGORITHM</span><span class="o">=</span><span class="n">UNDEFINED</span> <span class="k">DEFINER</span><span class="o">=</span><span class="nv">`admin`</span><span class="o">@</span><span class="nv">`localhost`</span> <span class="k">VIEW</span> <span class="nv">`exportacion_proyectos_view`</span> <span class="k">AS</span> <span class="k">SELECT</span> <span class="k">DISTINCT</span> <span class="n">p</span><span class="p">.</span><span class="n">idProyecto</span> <span class="nv">&quot;idProyecto&quot;</span><span class="p">,</span> <span class="n">j</span><span class="p">.</span><span class="n">nombre</span> <span class="nv">&quot;nombreJurisidiccion&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">nombre</span> <span class="nv">&quot;nombreProyecto&quot;</span><span class="p">,</span> </span>&#x000A;<span id="LC43" class="line">			  <span class="n">p</span><span class="p">.</span><span class="n">estado</span><span class="p">,</span> <span class="n">oj</span><span class="p">.</span><span class="n">nombre</span> <span class="nv">&quot;objetivoEstrategico&quot;</span><span class="p">,</span> <span class="n">oop</span><span class="p">.</span><span class="n">nombre</span> <span class="nv">&quot;objetivoOperativo&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">descripcion</span> <span class="nv">&quot;descripcionProyecto&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">liderProyecto</span> <span class="nv">&quot;responsable&quot;</span><span class="p">,</span></span>&#x000A;<span id="LC44" class="line">			   <span class="n">a</span><span class="p">.</span><span class="n">nombre</span> <span class="nv">&quot;area&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">organismosCorresponsables</span> <span class="nv">&quot;organismosCorresponsables&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">fechaInicio</span> <span class="nv">&quot;fechaInicio&quot;</span><span class="p">,</span></span>&#x000A;<span id="LC45" class="line">			  <span class="n">p</span><span class="p">.</span><span class="n">fechaFin</span> <span class="nv">&quot;fechaFin&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">tipoProyecto</span> <span class="nv">&quot;tipoProyecto&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">cambioLegislativo</span> <span class="nv">&quot;implicaCambioLegislativo&quot;</span><span class="p">,</span></span>&#x000A;<span id="LC46" class="line">			   <span class="n">p</span><span class="p">.</span><span class="n">prioridadJurisdiccional</span> <span class="nv">&quot;prioridadJurisdiccional&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">meta</span> <span class="nv">&quot;meta&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">unidadMeta</span> <span class="nv">&quot;unidadMeta&quot;</span><span class="p">,</span> <span class="n">p</span><span class="p">.</span><span class="n">poblacionAfectada</span> <span class="nv">&quot;cantidadPoblacionImpactada&quot;</span><span class="p">,</span></span>&#x000A;<span id="LC47" class="line">			   <span class="k">NULL</span> <span class="nv">&quot;prioridadJefatura&quot;</span><span class="p">,</span>  <span class="k">NULL</span> <span class="nv">&quot;estadoAprobacion&quot;</span></span>&#x000A;<span id="LC48" class="line"><span class="k">FROM</span> <span class="n">jurisdiccion</span> <span class="n">j</span></span>&#x000A;<span id="LC49" class="line"><span class="k">INNER</span> <span class="k">JOIN</span> <span class="n">objetivo_jurisdiccional</span> <span class="n">oj</span> <span class="k">ON</span> <span class="n">oj</span><span class="p">.</span><span class="n">idJurisdiccion</span> <span class="o">=</span> <span class="n">j</span><span class="p">.</span><span class="n">idJurisdiccion</span></span>&#x000A;<span id="LC50" class="line"><span class="k">INNER</span> <span class="k">JOIN</span> <span class="n">objetivo_operativo</span> <span class="n">oop</span> <span class="k">ON</span> <span class="n">oop</span><span class="p">.</span><span class="n">idObjetivoOperativo</span> <span class="o">=</span> <span class="n">oj</span><span class="p">.</span><span class="n">idObjetivoJurisdiccional</span></span>&#x000A;<span id="LC51" class="line"><span class="k">INNER</span> <span class="k">JOIN</span> <span class="n">proyecto</span> <span class="n">p</span> <span class="k">ON</span> <span class="n">p</span><span class="p">.</span><span class="n">idObjetivoOperativo</span> <span class="o">=</span> <span class="n">oop</span><span class="p">.</span><span class="n">idObjetivoOperativo</span></span>&#x000A;<span id="LC52" class="line"><span class="k">LEFT</span> <span class="k">JOIN</span> <span class="n">area</span> <span class="n">a</span> <span class="k">on</span> <span class="n">a</span><span class="p">.</span><span class="n">idArea</span> <span class="o">=</span> <span class="n">p</span><span class="p">.</span><span class="n">idArea</span></span>&#x000A;<span id="LC53" class="line"><span class="k">where</span> <span class="n">p</span><span class="p">.</span><span class="n">estado</span> <span class="k">in</span> <span class="p">(</span><span class="nv">&quot;verificado&quot;</span><span class="p">,</span> <span class="nv">&quot;presentado&quot;</span><span class="p">)</span></span>&#x000A;<span id="LC54" class="line"><span class="k">order</span> <span class="k">by</span> <span class="mi">1</span><span class="p">,</span><span class="mi">2</span><span class="p">,</span><span class="mi">3</span><span class="p">,</span><span class="mi">4</span><span class="p">,</span><span class="mi">5</span><span class="p">,</span><span class="mi">6</span> <span class="p">;</span></span>&#x000A;<span id="LC55" class="line"><span class="cm">/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, &#39;&#39;) */</span><span class="p">;</span></span>&#x000A;<span id="LC56" class="line"><span class="cm">/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */</span><span class="p">;</span></span>&#x000A;<span id="LC57" class="line"><span class="cm">/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */</span><span class="p">;</span></span></code></pre>&#x000A;
</div>

</div>

</article>
</div>

</div>
<div class='modal' id='modal-remove-blob'>
<div class='modal-dialog'>
<div class='modal-content'>
<div class='modal-header'>
<a class='close' data-dismiss='modal' href='#'>×</a>
<h3 class='page-title'>Remove 3-CREATE_exportacion_proyectos_view.sql</h3>
<p class='light'>
From branch
<strong>develop</strong>
</p>
</div>
<div class='modal-body'>
<form accept-charset="UTF-8" action="/proyectosBA/back-end/blob/develop/configuracion/database/3-CREATE_exportacion_proyectos_view.sql" class="form-horizontal js-requires-input" method="post"><div style="display:none"><input name="utf8" type="hidden" value="&#x2713;" /><input name="_method" type="hidden" value="delete" /><input name="authenticity_token" type="hidden" value="FBUnHx5fCeMVaMieKaFJRRk9r6oiTD7YrmQNYAZzpWU=" /></div>
<div class='form-group commit_message-group'>
<label class="control-label" for="commit_message">Commit message
</label><div class='col-sm-10'>
<div class='commit-message-container'>
<div class='max-width-marker'></div>
<textarea class="form-control" id="commit_message" name="commit_message" placeholder="Removed this file because..." required="required" rows="3">
</textarea>
</div>
</div>
</div>

<div class='form-group'>
<div class='col-sm-offset-2 col-sm-10'>
<button class="btn btn-remove btn-remove-file" name="button" type="submit">Remove file</button>
<a class="btn btn-cancel" data-dismiss="modal" href="#">Cancel</a>
</div>
</div>
</form>

</div>
</div>
</div>
</div>


</div>
</div>
</div>
</div>
</div>

<script>
  GitLab.GfmAutoComplete.dataSource = "/proyectosBA/back-end/autocomplete_sources?type=NilClass&type_id=develop%2Fconfiguracion%2Fdatabase%2F3-CREATE_exportacion_proyectos_view.sql"
  GitLab.GfmAutoComplete.setup();
</script>


</body>
</html>

